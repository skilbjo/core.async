(ns jobs.altsgo
  (:require [clojure.string :as string]
            [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]
            [core.async.util :as util])
  (:gen-class))

(defn fan-in [ins]
  (let [c (chan)]
    (go (while true
          (let [[x] (alts! ins)]
            (>! c x))))
    c))

(defn fan-out [in cs-or-n]
  (let [cs (if (number? cs-or-n)
             (repeatedly cs-or-n chan)
             cs-or-n)]
    (go (while true
          (let [x (<! in)
                outs (map #(vector % x) cs)]
            (alts! outs))))
    cs))

(defn -main []
  (println "in core")
  (let [cout (chan)
      cin (fan-in (fan-out cout (repeatedly 3 chan)))]
  (go (dotimes [n 10]
        (>! cout n)
        (prn (<! cin))))
  nil))

