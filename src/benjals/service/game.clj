(ns benjals.service.game
  (:require [benjals.model.game :as game]
            [benjals.model.game-attendance :as attendance]
            [benjals.service.db :as db]))

(defn get-games [team-id]
  (db/persistent-con
    (fn [db-spec]
      (game/get-all db-spec team-id))))

(defn get-game [game-id]
  (db/persistent-con
    (fn [db-spec]
      (game/get-by-id db-spec game-id))))

(defn get-players [teamId id]
  (db/persistent-con
    (fn [db-spec]
      (game/get-players db-spec teamId id))))

(defn create-game [teamId game]
  (db/persistent-tcon
    (fn [db-spec]
      (game/create db-spec teamId game))))

(defn set-attending [attendance]
  (db/persistent-tcon
    (fn [db-spec]
      (attendance/create db-spec attendance))))
