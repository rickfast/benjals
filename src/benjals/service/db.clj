(ns benjals.service.db
  (:require [clojure.java.jdbc :as sql]))

(defn- build-spec []
  {:connection (sql/get-connection (System/getenv "DATABASE_URL"))})

(defn persistent-con [db-func]
  (let [db-spec (build-spec)]
    (with-open [db-con (db-spec :connection)]
      (db-func db-spec))))

(defn persistent-tcon [db-func]
  (let [db-spec (build-spec)]
    (with-open [db-con (db-spec :connection)]
      (sql/db-transaction [t-con db-spec]
        (db-func t-con)))))