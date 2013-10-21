(ns benjals.util.gravatar
  (:require [digest]))

(defn get-avatar-url [email]
  (let [hash (digest/md5 (.getBytes email "CP1252"))]
    (format "http://www.gravatar.com/avatar/%s" hash)))