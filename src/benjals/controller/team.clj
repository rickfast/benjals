(ns benjals.controller.team
  (:use [compojure.core :only (defroutes context GET POST PUT DELETE)]
        [ring.util.response])
  (:require [benjals.service.team :as service]))

(defn index-teams []
  (response (service/get-teams)))

(defn create-team [session team]
  (response (service/create-team (:id (:session-user session)) team)))

(defn get-team [id]
  (let [result (service/get-team id)]
    (cond
      (nil? result) {:status 404}
      :else (response result))))

(defn index-players [id]
  (let [result (service/get-players id)]
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
      (POST "/" {session :session, body :body } (create-team session body))
      (context "/:id" [id]
        (let [id (read-string id)]
          (defroutes team-routes
            (GET "/players" [] (index-players id))
            (GET "/" [] (get-team id))
            (PUT "/" {body :body} (update-team id body))
            (DELETE "/" [] (delete-team id))))))))