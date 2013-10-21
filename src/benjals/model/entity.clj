(ns benjals.model.entity
  (:require [clojure.java.jdbc :as sql]))

(defn get-all [db table]
  (sql/query db
    [(str "select * from " table " order by id desc")]))

(defn get-by-id [db table id]
  (let [results (sql/query db
                  [(str "select * from " table " where id = ?") id])]
    (cond
      (empty? results) nil
      :else (first results))))

(defn create [db table value-map]
  (let [result (sql/insert! db (keyword table) value-map)]
    (first result)))