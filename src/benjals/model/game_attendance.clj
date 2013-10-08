(ns benjals.model.game-attendance
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(def table-name "game_attendances")
(def db-url (System/getenv "DATABASE_URL"))

(defn create [attendance]
  (entity/create table-name attendance db-url))