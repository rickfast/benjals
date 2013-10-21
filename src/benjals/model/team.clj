(ns benjals.model.team
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(defn get-players [db id]
  (sql/query db
    ["select users.*, users_teams.alternate from users
      inner join users_teams on (users.id = users_teams.user_id)
      where users_teams.team_id = ?" id]))

(defn get-all [db]
  (entity/get-all db "teams"))

(defn get-by-id [db id]
  (entity/get-by-id db "teams" id))

(defn create [db creator-id team]
  (entity/create db "teams" team))

