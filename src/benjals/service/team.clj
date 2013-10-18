(ns benjals.service.team
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]
            [benjals.model.team :as team]
            [benjals.model.user :as user]))

(def db-url (System/getenv "DATABASE_URL"))

(defn get-teams []
  (sql/with-connection db-url
    (team/get-all)))

(defn link-players [players team]
  (assoc team "players" (map (fn [player]
                               (sql/with-connection db-url
                                 (sql/transaction
                                   (entity/create "users_teams"
                                     {"user_id" (player :id), "team_id" (team :id), "alternate" (player :alternate)})
                                   player)))
                          players)))

(defn create-team [creator-id {players "players", :as team}]
  (sql/with-connection db-url
    (sql/transaction
      (let [team (team/create creator-id (-> team (dissoc "players") (assoc "creator_id" creator-id)))]
        (link-players (map (fn [player]
                             (sql/with-connection db-url
                               (sql/transaction
                                 (let [existing-user (user/get-by-email (player "email"))]
                                   (assoc (cond
                                            (nil? existing-user) (user/create player)
                                            :else existing-user)
                                     :alternate (player "alternate"))))))
                        players)
          team)))))

(defn get-team [id]
  (sql/with-connection db-url
    (team/get-by-id id)))

(defn get-players [id]
  (sql/with-connection db-url
    (team/get-players id)))

