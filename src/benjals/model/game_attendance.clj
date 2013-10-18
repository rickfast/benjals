(ns benjals.model.game-attendance
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(defn create [attendance]
  (entity/create "game_attendances" attendance))