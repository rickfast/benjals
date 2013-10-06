(ns benjals.model.game
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(def table-name "games")
(def db-url (System/getenv "DATABASE_URL"))

(defn get-all []
  (entity/get-all table-name db-url))

(defn get-by-id [id]
  (entity/get-by-id table-name id db-url))

(defn create [teamId game]
  (sql/with-connection db-url
    (sql/do-prepared-return-keys "insert into games (team_id, start_time) values (?, to_timestamp(?))"
      [teamId (game "start_time")])))