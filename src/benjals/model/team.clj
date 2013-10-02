(ns benjals.model.team
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.user :as user]))

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

(defn create [{players "players", :as team}]
  (let [team (dissoc team "players")]
    (map user/create players)
    (sql/with-connection (System/getenv "DATABASE_URL")
      (sql/insert-values :teams [:name] (vals team)))))