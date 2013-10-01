(ns benjals.controller.user
  (:use [compojure.core :only (defroutes context GET POST PUT DELETE)]
        [ring.util.response])
  (:require [benjals.model.user :as model]))

(defn create-user [user]
  (response (model/create user)))

(defn get-user [id]
  (def result (model/get-by-id (read-string id)))
  (cond
    (nil? result) {:status 404}
    :else (response result)))

(defn update-user [id user]
  (response user))

(defn delete-user [id]
  (response id))

(defroutes routes
  (context "/users" []
    (defroutes users-routes
      (POST "/" {body :body} (create-user body))
      (context "/:id" [id]
        (defroutes user-routes
          (GET "/" [] (get-user id))
          (PUT "/" {body :body} (update-user id body))
          (DELETE "/" [] (delete-user id)))))))