(ns benjals.service.user
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.user :as user]
            [benjals.util.gravatar :as gravatar]))

(def db-url (System/getenv "DATABASE_URL"))

(defn- user-with-avatar [user]
  (cond (not (nil? user))
    (assoc user :avatar
      (gravatar/get-avatar-url (get user :email)))
    :else nil))

(defn get-user [user-id]
  (sql/with-connection db-url
    (user-with-avatar (user/get-by-id user-id))))

(defn get-user-by-email [email]
  (sql/with-connection db-url
    (let [user (user/get-by-email email)]
      (cond
        (nil? user) nil
        :else (user-with-avatar user)))))

(defn create-user [user]
  (sql/with-connection db-url
    (sql/transaction
      (user-with-avatar (user/create user)))))
