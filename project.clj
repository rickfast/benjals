(defproject benjals "0.1.0-SNAPSHOT"
  :description "Benjals"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :uberjar-name "benjals.jar"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [postgresql "9.1-901.jdbc4"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [ring/ring-json "0.2.0"]
                 [compojure "1.1.3"]
                 [clj-dbcp      "0.8.0"]
                 [clj-liquibase "0.4.0"]
                 [oss-jdbc      "0.8.0"]
                 [digest "1.4.3"]
                 [clj-http "0.7.7"]
                 [clojurewerkz/quartzite "1.1.0"]]
  :main benjals.core)
