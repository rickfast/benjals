(ns benjals.middleware.auth
  (:use [ring.util.response]))

(defn check-logged-in
  [handler]
  (fn [request]
    (if-let [user (:session-user (:session request))]
      (handler request)
      {:status 403})))