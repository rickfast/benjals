(ns benjals.controller.team
  (:use [compojure.core :only (defroutes context GET POST PUT DELETE)]
        [ring.util.response])
  (:require [benjals.model.team :as model]))

(defn index-teams []
  (response (model/get-all)))

(defn create-team [team]
  (response (model/create team)))

(defn get-team [id]
  (let [result (model/get-by-id id)]
    (cond
      (nil? result) {:status 404}
      :else (response result))))

(defn get-players [id]
  (let [result (model/get-players id)]
    (cond
      (empty? result) {:status 404}
      :else (response result))))

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
        (let [id (read-string id)]
          (defroutes team-routes
            (GET "/players" [] (get-players id))
            (GET "/" [] (get-team id))
            (PUT "/" {body :body} (update-team id body))
            (DELETE "/" [] (delete-team id))))))))