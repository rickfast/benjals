(ns benjals.service.user
  (:require [benjals.model.user :as user]
            [benjals.util.gravatar :as gravatar]
            [benjals.service.db :as db]))

(defn- user-with-avatar [user]
  (cond (not (nil? user))
    (assoc user :avatar
      (gravatar/get-avatar-url (user :email)))
    :else nil))

(defn get-user [user-id]
  (db/persistent-con
    (fn [db-spec]
      (user-with-avatar (user/get-by-id db-spec user-id)))))

(defn get-user-by-email [email]
  (db/persistent-con
    (fn [db-spec]
      (let [user (user/get-by-email db-spec email)]
        (cond
          (nil? user) nil
          :else (user-with-avatar user))))))

(defn create-user [user]
  (db/persistent-tcon
    (fn [db-spec]
      (user-with-avatar (user/create db-spec user)))))
