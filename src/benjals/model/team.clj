(ns benjals.model.team
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]
            [benjals.model.user :as user]))

(def table-name "teams")
(def db-url (System/getenv "DATABASE_URL"))

(defn link-players [players team]
  (assoc team "players" (map (fn [player]
                               (entity/create "users_teams"
                                 {"user_id" (player :id), "team_id" (team :id), "alternate" (player :alternate)}
                                 db-url)
                               player)
                          players)))

(defn get-players [id]
  (sql/with-connection db-url
    (sql/with-query-results results
      ["select users.id, users.email, users.first, users.last, users_teams.alternate from users
        inner join users_teams on (users.id = users_teams.user_id) where users_teams.team_id = ?" id]
      (into [] results))))

(defn get-all []
  (entity/get-all table-name db-url))

(defn get-by-id [id]
  (entity/get-by-id table-name id db-url))

(defn create [creator-id {players "players", :as team}]
  (let [team (entity/create table-name (-> team (dissoc "players") (assoc "creator_id" creator-id)) db-url)]
    (link-players (map (fn [player]
                         (let [existing-user (user/get-by-email (player "email"))]
                           (assoc (cond
                                    (nil? existing-user) (user/create player)
                                    :else existing-user)
                             :alternate (player "alternate"))))
                    players)
      team)))

