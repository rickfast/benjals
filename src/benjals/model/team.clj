(ns benjals.model.team
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(defn get-players [id]
  (sql/with-query-results results
    ["select users.*, users_teams.alternate from users
      inner join users_teams on (users.id = users_teams.user_id)
      where users_teams.team_id = ?" id]
    (into [] results)))

(defn get-all []
  (entity/get-all "teams"))

(defn get-by-id [id]
  (entity/get-by-id "teams" id))

(defn create [creator-id team]
  (entity/create "teams" team))

