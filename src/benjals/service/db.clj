(ns benjals.service.db
  (:require [clojure.java.jdbc :as sql])
  (:import com.mchange.v2.c3p0.ComboPooledDataSource))

(defn- pool [url]
  (let [cpds (doto (ComboPooledDataSource.)
               (.setJdbcUrl (str "jdbc:" url))
               (.setMaxIdleTimeExcessConnections (* 30 60))
               (.setMaxIdleTime (* 3 60 60)))]
    {:datasource cpds}))

(def pooled-db (delay (pool (System/getenv "DATABASE_URL"))))

(defn db-connection [] @pooled-db)

(def db-spec {:connection (sql/get-connection (db-connection))})

(defn persistent-con [db-func]
  (db-func db-spec))

(defn persistent-tcon [db-func]
  (sql/db-transaction [t-con db-spec]
    (db-func t-con)))