(ns benjals.core
  (:use [compojure.core :only (defroutes GET POST)]
        [ring.adapter.jetty :as ring]
        [ring.middleware.json :only (wrap-json-response wrap-json-body)]
        [benjals.model.migration :only (migrate)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.util.response :as resp]
            [benjals.controller.team :as team]))

(defroutes routes
  team/routes
  (route/resources "/")
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api routes)
    (wrap-json-response) (wrap-json-body)))

(defn -main []
  (println (System/getenv "DATABASE_URL"))
  (migrate (System/getenv "DATABASE_URL"))
  (let
    [port-env (System/getenv "PORT")
     port (if-not (nil? port-env) (Integer/valueOf port-env) 8080)]
    (run-jetty (var app) {:port port :join? false})))