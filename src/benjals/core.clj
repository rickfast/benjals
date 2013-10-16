(ns benjals.core
  (:use [compojure.core :only (defroutes context GET POST)]
        [ring.adapter.jetty :as ring]
        [ring.middleware.json :only (wrap-json-response wrap-json-body)]
        [ring.middleware.session :only (wrap-session)]
        [benjals.middleware.auth :only (check-logged-in)]
        [benjals.model.migration :only (migrate)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.util.response :as resp]
            [benjals.controller.team :as team]
            [benjals.controller.user :as user]
            [benjals.controller.game :as game]
            [benjals.controller.unsecured-user :as unsecured-user]
            [benjals.job.schedule :as schedule]))

(defroutes api-routes
  team/routes
  user/routes
  game/routes)

(defroutes app-routes
  (context "/api/unsecured/user" []
    (-> (handler/api unsecured-user/routes) (wrap-json-body) (wrap-json-response)))
  (context "/api" []
    (-> (handler/api api-routes) (check-logged-in) (wrap-json-body) (wrap-json-response))))

(defroutes app
  (-> app-routes (wrap-session))
  (route/resources "/")
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))
  (route/not-found "Not Found"))

(defn -main []
  (migrate (System/getenv "DATABASE_URL"))
  (schedule/start-scheduler)
  (let
    [port-env (System/getenv "PORT")
     port (if-not (nil? port-env) (Integer/valueOf port-env) 8080)]
    (run-jetty (var app) {:port port :join? false})))