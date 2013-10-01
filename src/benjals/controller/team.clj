(ns benjals.controller.team
  (:use [compojure.core :only (defroutes context GET POST PUT DELETE)]
        [ring.util.response])
  (:require [benjals.model.team :as model]))

(defn index-teams []
  (response (model/all)))

(defn create-team [team]
  (response (model/create team)))

(defn get-team [id]
  (response id))

(defn update-team [id team]
  (response team))

(defn delete-team [id]
  (response id))

(defroutes routes
  (context "/teams" []
    (defroutes teams-routes
      (GET  "/" [] (index-teams))
      (POST "/" {body :body} (create-team body))
      (context "/:id" [id]
        (defroutes team-routes
          (GET "/" [] (get-team id))
          (PUT "/" {body :body} (update-team id body))
          (DELETE "/" [] (delete-team id)))))))