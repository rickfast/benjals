(ns benjals.controller.game
  (:use [compojure.core :only (defroutes context GET POST PUT DELETE)]
        [ring.util.response])
  (:require [benjals.service.game :as service]))

(defn index-games [team-id]
  (response (service/get-games team-id)))

(defn create-game [teamId game]
  (response (service/create-game teamId game)))

(defn get-game [game-id]
  (let [result (service/get-game game-id)]
    (cond
      (nil? result) {:status 404}
      :else (response result))))

(defn index-players [teamId id]
  (let [result (service/get-players teamId id)]
    (cond
      (empty? result) {:status 404}
      :else (response result))))

(defn set-attending [session game-id attending]
  (service/set-attending {:game_id game-id, :user_id (:id (:session-user session)), :attending attending}))

(defn update-game [id game]
  (response game))

(defn delete-game [id]
  (response id))

(defroutes routes
  (context "/teams/:teamId/games" [teamId]
    (let [teamId (read-string teamId)]
      (defroutes games-routes
        (GET  "/" [] (index-games teamId))
        (POST "/" {body :body} (create-game teamId body))
        (context "/:id" [id]
          (let [id (read-string id)]
            (defroutes game-routes
              (GET "/players" [] (index-players teamId id))
              (POST "/attending" {session :session} (set-attending session id true))
              (POST "/not-attending" {session :session} (set-attending session id false))
              (GET "/" [] (get-game id))
              (PUT "/" {body :body} (update-game id body))
              (DELETE "/" [] (delete-game id)))))))))