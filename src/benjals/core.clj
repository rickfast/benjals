(ns benjals.core
  (:use [compojure.core :only (defroutes GET POST)]
        [ring.adapter.jetty :as ring]
        [ring.middleware.json :only (wrap-json-response wrap-json-body)]
        [benjals.model.migration :only (migrate)]
        [benjals.model.team]))

;; This stuff needs to be refactored in controller namespaces (see: https://devcenter.heroku.com/articles/clojure-web-application)
(defroutes routes
  (GET "/teams" [] {:body (all)})
  (POST "/teams" {body :body} {:body (create body)}))

(def app
  (-> routes
    (wrap-json-response) (wrap-json-body)))

(defn -main []
  (println (System/getenv "DATABASE_URL"))
  (migrate (System/getenv "DATABASE_URL"))
  (let
    [port-env System/getenv "PORT"
     port (if-not (nil? port-env) port-env "8080")])
  (run-jetty (var app) {:port port :join? false}))