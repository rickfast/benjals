(ns benjals.model.entity
  (:require [clojure.java.jdbc :as sql]))

(defn get-all [table db-url]
  (sql/with-connection db-url
    (sql/with-query-results results
      [(str "select * from " table " order by id desc")]
      (into [] results))))

(defn get-by-id [table id db-url]
  (sql/with-connection db-url
    (sql/with-query-results results
      [(str "select * from " table " where id = ?") id]
      (cond
        (empty? results) nil
        :else (first results)))))

(defn create [table value-map db-url]
  (sql/with-connection db-url
    (sql/insert-values (keyword table) (keys value-map) (vals value-map))))