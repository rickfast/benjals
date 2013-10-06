(ns benjals.model.user
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(def table-name "users")
(def db-url (System/getenv "DATABASE_URL"))

(defn get-by-id [id]
  (entity/get-by-id table-name id db-url))

(defn get-by-email [email]
  (sql/with-connection db-url
    (sql/with-query-results results
      ["select * from users where email = ?" email]
      (cond
        (empty? results) nil
        :else (first results)))))

(defn create [user]
  (entity/create table-name user db-url))