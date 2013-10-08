(ns benjals.controller.unsecured-user
  (:use [compojure.core :only (defroutes POST GET)]
        [ring.util.response])
  (:require [benjals.model.user :as model]))

(defn create-user [user]
  (let [user (model/create user)]
    (if-not (nil? user)
      {:status 200
       :body (dissoc user :password)
       :session {:session-user user}}
      {:status 403 :body "Create Failed"})))

(defn- log-in [user]
  (let [user (model/get-by-email (get user "email"))]
    (if-not (nil? user)
      {:status 200
       :body (dissoc user :password)
       :session {:session-user user}}
      {:status 403 :body "Login Failed"})))

(defn- log-out []
  {:status 200 :body {} :session {}})

(defroutes routes
  (POST "/create" {body :body} (create-user body))
  (POST "/login" {body :body} (log-in body))
  (GET "/logout" [] (log-out)))