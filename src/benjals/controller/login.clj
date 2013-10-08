(ns benjals.controller.login
  (:use [compojure.core :only (defroutes POST)])
  (:require [benjals.model.user :as model]))

(defn- log-in [user]
  (let [user (model/get-by-email (get user "email"))]
    (if-not (nil? user)
      {:status 200
       :body (dissoc user :password)
       :session {:session-user user}}
      {:status 403 :body "Login Failed"})))

(defroutes routes
  (POST "/" {body :body} (log-in body)))