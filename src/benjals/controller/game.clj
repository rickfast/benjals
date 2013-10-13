(ns benjals.controller.game
  (:use [compojure.core :only (defroutes context GET POST PUT DELETE)]
        [ring.util.response])
  (:require [benjals.model.game :as model]
            [benjals.model.game-attendance :as attendance]))

(defn index-games [team-id]
  (response (model/get-all team-id)))

(defn create-game [teamId game]
  (response (model/create teamId game)))

(defn get-game [game-id]
  (let [result (model/get-by-id game-id)]
    (cond
      (nil? result) {:status 404}
      :else (response result))))

(defn get-players [teamId id]
  (let [result (model/get-players teamId id)]
    (cond
      (empty? result) {:status 404}
      :else (response result))))

(defn update-game [id game]
  (response game))

(defn delete-game [id]
  (response id))

(defn set-attending [session game-id attending]
  (attendance/create {:game_id game-id, :user_id (:id (:session-user session)), :attending attending}))

(defroutes routes
  (context "/teams/:teamId/games" [teamId]
    (let [teamId (read-string teamId)]
      (defroutes games-routes
        (GET  "/" [] (index-games teamId))
        (POST "/" {body :body} (create-game teamId body))
        (context "/:id" [id]
          (let [id (read-string id)]
            (defroutes game-routes
              (GET "/players" [] (get-players teamId id))
              (POST "/attending" {session :session} (set-attending session id true))
              (POST "/not-attending" {session :session} (set-attending session id false))
              (GET "/" [] (get-game id))
              (PUT "/" {body :body} (update-game id body))
              (DELETE "/" [] (delete-game id)))))))))