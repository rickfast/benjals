(ns benjals.model.game
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(def table-name "games")
(def db-url (System/getenv "DATABASE_URL"))

(defn get-attendance [game-id user-id]
  (sql/with-connection db-url
    (sql/with-query-results results
      ["select attending from game_attendances where game_id = ? and user_id = ?" game-id user-id]
      (cond
        (empty? results) nil
        :else (first results)))))

(defn get-all [team-id]
  (sql/with-connection db-url
    (sql/with-query-results results
      ["select * from games where team_id = ? order by id desc" team-id]
      (into [] results))))

(defn get-by-id [game-id user-id]
  (assoc (entity/get-by-id table-name game-id db-url) "attending" (get-attendance game-id user-id)))

(defn create [teamId game]
  (sql/with-connection db-url
    (sql/do-prepared-return-keys "insert into games (team_id, start_time) values (?, to_timestamp(?))"
      [teamId (game "start_time")])))