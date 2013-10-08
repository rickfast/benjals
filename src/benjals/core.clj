(ns benjals.core
  (:use [compojure.core :only (defroutes context GET POST)]
        [ring.adapter.jetty :as ring]
        [ring.middleware.json :only (wrap-json-response wrap-json-body)]
        [sandbar.stateful-session :only (wrap-stateful-session)]
        [benjals.middleware.auth :only (check-logged-in)]
        [benjals.model.migration :only (migrate)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.util.response :as resp]
            [benjals.controller.team :as team]
            [benjals.controller.user :as user]
            [benjals.controller.game :as game]
            [benjals.controller.login :as login]))

(defroutes api-routes
  team/routes
  user/routes
  game/routes)

(defroutes app-routes
  (context "/login" []
    (-> (handler/api login/routes) (wrap-json-body)))
  (context "/api" []
    (-> (handler/api api-routes) (check-logged-in) (wrap-json-body) (wrap-json-response))))

(defroutes app
  (-> app-routes (wrap-stateful-session))
  (route/resources "/")
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))
  (route/not-found "Not Found"))

(defn -main []
  (migrate (System/getenv "DATABASE_URL"))
  (let
    [port-env (System/getenv "PORT")
     port (if-not (nil? port-env) (Integer/valueOf port-env) 8080)]
    (run-jetty (var app) {:port port :join? false})))