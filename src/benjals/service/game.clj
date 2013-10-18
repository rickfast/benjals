(ns benjals.service.game
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.game :as game]
            [benjals.model.game-attendance :as attendance]))

(def db-url (System/getenv "DATABASE_URL"))

(defn get-games [team-id]
  (sql/with-connection db-url
    (game/get-all team-id)))

(defn create-game [teamId game]
  (sql/with-connection db-url
    (sql/transaction
      (game/create teamId game))))

(defn get-game [game-id]
  (sql/with-connection db-url
    (game/get-by-id game-id)))

(defn get-players [teamId id]
  (sql/with-connection db-url
    (game/get-players teamId id)))

(defn set-attending [attendance]
  (sql/with-connection db-url
    (sql/transaction
      (attendance/create attendance))))
