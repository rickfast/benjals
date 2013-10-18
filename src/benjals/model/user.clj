(ns benjals.model.user
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(defn get-by-id [id]
  (entity/get-by-id "users" id))

(defn get-by-email [email]
  (sql/with-query-results results
    ["select * from users where email = ?" email]
    (cond
      (empty? results) nil
      :else (first results))))

(defn create [user]
  (entity/create "users" user))