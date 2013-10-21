(ns benjals.service.team
  (:require [benjals.model.entity :as entity]
            [benjals.model.team :as team]
            [benjals.model.user :as user]
            [benjals.service.db :as db]))

(defn- link-players [db-spec players team]
  (assoc team "players" (map (fn [player]
                               (entity/create db-spec "users_teams"
                                 {"user_id" (player :id), "team_id" (team :id), "alternate" (player :alternate)})
                               player)
                          players)))

(defn get-teams []
  (db/persistent-con
    (fn [db-spec]
      (team/get-all db-spec))))

(defn get-team [id]
  (db/persistent-con
    (fn [db-spec]
      (team/get-by-id db-spec id))))

(defn get-players [id]
  (db/persistent-con
    (fn [db-spec]
      (team/get-players db-spec id))))

(defn create-team [creator-id {players "players", :as team}]
  (db/persistent-tcon
    (fn [db-spec]
      (let [team (team/create db-spec creator-id (-> team (dissoc "players") (assoc "creator_id" creator-id)))]
        (link-players db-spec (map (fn [player]
                                     (let [existing-user (user/get-by-email db-spec (player "email"))]
                                       (assoc (cond
                                                (nil? existing-user) (user/create db-spec player)
                                                :else existing-user)
                                         :alternate (player "alternate"))))
                                players)
          team)))))