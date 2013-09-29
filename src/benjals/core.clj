(ns benjals.core
  (:use [compojure.core :only (defroutes GET)]
        [ring.adapter.jetty :as ring]
        [ring.middleware.json :only (wrap-json-response)]))

(defroutes routes
  (GET "/teams" [] {:body {:name "Team 1"}}))

(def app
  (-> routes
    (wrap-json-response)))

(defn -main []
  (run-jetty (var app) {:port 8080 :join? false}))