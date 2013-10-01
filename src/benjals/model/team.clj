(ns benjals.model.team
  (:require [clojure.java.jdbc :as sql]))

(defn get-all []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from teams order by id desc"]
      (into [] results))))

(defn get-by-id [id]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from teams where id = ?" id]
      (cond
        (empty? results) nil
        :else (first results)))))

(defn create [team]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/insert-values :teams [:name] (vals team))))