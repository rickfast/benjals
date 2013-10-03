(ns benjals.model.user
  (:require [clojure.java.jdbc :as sql]))

(defn get-by-id [id]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from users where id = ?" id]
      (cond
        (empty? results) nil
        :else (first results)))))

(defn create [user]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/insert-values :users (keys user) (vals user))))