(ns benjals.model.user
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(defn get-by-id [db id]
  (entity/get-by-id db "users" id))

(defn get-by-email [db email]
  (let [results (sql/query db
                  ["select * from users where email = ?" email])]
    (cond
      (empty? results) nil
      :else (first results))))

(defn create [db user]
  (entity/create db "users" user))