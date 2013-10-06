(ns benjals.controller.game
  (:use [compojure.core :only (defroutes context GET POST PUT DELETE)]
        [ring.util.response])
  (:require [benjals.model.game :as model]))

(defn index-games []
  (response (model/get-all)))

(defn create-game [teamId game]
  (response (model/create teamId game)))

(defn get-game [id]
  (let [result (model/get-by-id id)]
    (cond
      (nil? result) {:status 404}
      :else (response result))))

(defn update-game [id game]
  (response game))

(defn delete-game [id]
  (response id))

(defroutes routes
  (context "/teams/:teamId/games" [teamId]
    (let [teamId (read-string teamId)]
      (defroutes games-routes
        (GET  "/" [] (index-games))
        (POST "/" {body :body} (create-game teamId body))
        (context "/:id" [id]
          (let [id (read-string id)]
            (defroutes game-routes
              (GET "/" [] (get-game id))
              (PUT "/" {body :body} (update-game id body))
              (DELETE "/" [] (delete-game id)))))))))