(ns benjals.model.user
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]
            [benjals.util.gravatar :as gravatar]))

(def table-name "users")
(def db-url (System/getenv "DATABASE_URL"))

(defn- user-with-avatar [user]
  (cond (not (nil? user))
    (assoc user :avatar
      (gravatar/get-avatar-url (get user :email)))
    :else nil))

(defn get-by-id [id]
  (user-with-avatar (entity/get-by-id table-name id db-url)))

(defn get-by-email [email]
  (sql/with-connection db-url
    (sql/with-query-results results
      ["select * from users where email = ?" email]
      (cond
        (empty? results) nil
        :else (user-with-avatar (first results))))))

(defn create [user]
  (user-with-avatar (entity/create table-name user db-url)))