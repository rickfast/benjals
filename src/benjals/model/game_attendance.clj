(ns benjals.model.game-attendance
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(defn create [db attendance]
  (entity/create db "game_attendances" attendance))