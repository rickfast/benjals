(ns benjals.model.team
  (:require [clojure.java.jdbc :as sql]))

(defn all []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from team order by id desc"]
      (into [] results))))

(defn create [team]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/insert-values :team [:name] (vals team))))