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

(def ct-change2 (ch/create-table :user [[:id :int :null false :pk true :autoinc true]
                                        [:first [:varchar 40] :null false]
                                        [:last [:varchar 40] :null false]
                                        [:email [:varchar 40] :null false]]))
(def changeset-2 ["id=2" "author=johnvolk" [ct-change2]])

(def rt-change3 (ch/rename-table :user :users))
(def changeset-3 ["id=3" "author=johnvolk" [rt-change3]])

(def rt-change4 (ch/rename-table :team :teams))
(def changeset-4 ["id=4" "author=johnvolk" [rt-change4]])

(def mc-change5 (ch/drop-not-null-constraint :users :first))
(def changeset-5 ["id=5" "author=johnvolk" [mc-change5]])

(def mc-change6 (ch/drop-not-null-constraint :users :last))
(def changeset-6 ["id=6" "author=johnvolk" [mc-change6]])

(def ct-change7 (ch/create-table :users_teams [[:user_id :int :null false :refs "users" :fkname "fk_users"]
                                               [:team_id :int :null false :refs "teams" :fkname "fk_teams"]]))
(def changeset-7 ["id=7" "author=johnvolk" [ct-change7]])

(def ct-change8 (ch/create-table :games [[:id :int :null false :pk true :autoinc true]
                                         [:team_id :int :null false :refs "teams" :fkname "fk_teams"]
                                         [:start_time :timestamp :null false]]))
(def changeset-8 ["id=8" "author=johnvolk" [ct-change8]])

(def ac-change9 (ch/add-columns :users [[:password [:varchar 100]]]))
(def changeset-9 ["id=9" "author=rickfast" [ac-change9]])

(def i-change10 (ch/insert-data :users {:id -1 :first "Fagen" :last "Berry" :email "fagen.berry@faggleberry.com" :password ""}))
(def changeset-10 ["id=10" "author=rickfast" [i-change10]])

(def ct-change11 (ch/create-table :game_attendances [[:id :int :null false :pk true :autoinc true]
                                                     [:game_id :int :null false :refs "games" :fkname "fk_games"]
                                                     [:user_id :int :null false :refs "users" :fkname "fk_users"]
                                                     [:attending :boolean :null false]]))
(def changeset-11 ["id=11" "author=johnvolk" [ct-change11]])

(def ac-change12 (ch/add-columns :teams [[:required_head_count :int :null false]
                                        [:registration_deadline :int :null false]]))
(def changeset-12 ["id=12" "author=johnvolk" [ac-change12]])

(def ac-change13 (ch/add-columns :users_teams [[:alternate :boolean :null false]]))
(def changeset-13 ["id=13" "author=johnvolk" [ac-change13]])

(def ac-change14 (ch/add-columns :teams [[:creator_id :int :null false :refs "users(id)" :fkname "fk_users"]]))
(def changeset-14 ["id=14" "author=johnvolk" [ac-change14]])

(defchangelog app-changelog "benjals" [changeset-1 changeset-2 changeset-3 changeset-4 changeset-5 changeset-6
                                       changeset-7 changeset-8 changeset-9 changeset-10 changeset-11 changeset-12
                                       changeset-13 changeset-14])

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


