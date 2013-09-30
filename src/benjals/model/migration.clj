(ns benjals.model.migration
  (:require [clj-dbcp.core :as cp]
            [clj-liquibase.change :as ch]
            [clj-liquibase.cli :as cli]
            [clojure.string :as string])
  (:use [clj-liquibase.core :only (defchangelog)])
  (:import [java.net URI])
  (:import [java.util.regex Pattern]))


(def ct-change1 (ch/create-table :team [[:id :int :null false :pk true :autoinc true]
                                        [:name [:varchar 40] :null false]]))

(def changeset-1 ["id=1" "author=rickfast" [ct-change1]])

(defchangelog app-changelog "benjals" [changeset-1])

(defn- split-user-info [user-info]
  (let [user-tokens (string/split user-info (Pattern/compile ":"))]
    (cond
      (not (empty? user-tokens)) {:username (nth user-tokens 0) :password (nth user-tokens 1)}
      :else nil)))

(defn- get-user-info [url]
  (try
    (let [user-info (.getUserInfo url)]
      (cond
        (not (nil? user-info)) (split-user-info user-info)
        :else nil))
    (catch Exception e (.printStackTrace e))))

(defn- get-datasource [url]
  (let [uri (URI. url)
        user-info (get-user-info uri)
        jdbc-url (format "jdbc:postgresql://%s:%s%s" (.getHost uri) (.getPort uri) (.getPath uri))]
    (cp/make-datasource :jdbc (merge {:jdbc-url jdbc-url :classname "org.postgresql.Driver"} user-info))))

(defn migrate [url]
  (println url)
  (cli/update {:datasource (get-datasource url) :changelog app-changelog}))


