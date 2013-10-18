(ns benjals.job.schedule
  (:require [clojurewerkz.quartzite.scheduler :as qs]
            [clojurewerkz.quartzite.triggers :as t]
            [clojurewerkz.quartzite.jobs :as j]
            [clojurewerkz.quartzite.jobs :refer [defjob]]
            [clojurewerkz.quartzite.schedule.simple :refer [schedule repeat-forever with-interval-in-milliseconds]]))

;; Get rid of this, John.
(defjob DummyJob
  [ctx]
  (println "job is running"))

(defn start-scheduler []
  (qs/initialize)
  (qs/start)
  (let [job (j/build
              (j/of-type DummyJob)
              (j/with-identity (j/key "jobs.dummy")))
        trigger (t/build
                  (t/with-identity (t/key "triggers.1"))
                  (t/start-now)
                  (t/with-schedule (schedule
                                     (repeat-forever)
                                     (with-interval-in-milliseconds 600000))))]
    (qs/schedule job trigger)))