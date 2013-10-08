(ns benjals.controller.user
  (:use [compojure.core :only (defroutes context GET POST PUT DELETE)]
        [ring.util.response])
  (:require [benjals.model.user :as model]))

(defn create-user [user]
  (response (model/create user)))

(defn get-user [id]
  (let [result (model/get-by-id id)]
    (cond
      (nil? result) {:status 404}
      :else (response result))))

(defn get-user-by-email [email]
  (let [result (model/get-by-email email)]
    (cond
      (nil? result) {:status 404}
      :else (response result))))

(defn get-current-user [session]
  (cond
    (empty? session) {:status 404}
    :else (response (dissoc (:session-user session) :password))))

(defn update-user [id user]
  (response user))

(defn delete-user [id]
  (response id))

(defroutes routes
  (context "/users" []
    (defroutes users-routes
      (POST "/" {body :body} (create-user body))
      (context "/current" []
        (defroutes user-current-routes
          (GET "/" {session :session} (get-current-user session))))
      (context ["/:id", :id #"[0-9]+"] [id]
        (let [id (read-string id)]
          (defroutes user-id-routes
            (GET "/" [] (get-user id))
            (PUT "/" {body :body} (update-user id body))
            (DELETE "/" [] (delete-user id)))))
      (context "/:email" [email]
        (defroutes user-email-routes
          (GET "/" [] (get-user-by-email email)))))))