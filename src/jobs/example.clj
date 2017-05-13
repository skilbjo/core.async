(ns jobs.example
  (:require [clojure.string :as string]
            [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]
            [core.async.util :as util])
  (:gen-class))

(def echo-chan (chan))
(def hi-chan (chan))

(defn simple-example []
  (go (println (<! echo-chan)))
  (>!! echo-chan "ketchup"))

(defn complex-example []
  (doseq [n (range 1000)]
    (go (println (>! hi-chan (str "hi " n))))))

(defn hot-dog-machine []
  (let [in (chan)
        out (chan)]
    (go (<! in)
        (>! out "hot dog"))
    [in out]))

(defn hot-dog-machine-v2
  [hot-dog-count]
  (let [in (chan)
        out (chan)]
    (go (loop [hc hot-dog-count]
          (if (> hc 0)
            (let [input (<! in)]
              (if (= 3 input)
                (do (>! out "hot dog")
                    (recur (dec hc)))
                (do (>! out "wilted lettuce")
                    (recur hc))))
            (do (close! in)
                (close! out)))))
    [in out]))

(defn call-hotdog-machine-v2 []
  (let [[in out] (hot-dog-machine-v2 2)]
      (>!! in "pocket lint")
      (println (<!! out))

      (>!! in 3)
      (println (<!! out))

      (>!! in 3)
      (println (<!! out))

      (>!! in 3)
      (<!! out)))

(defn fan-in [ins]
  (let [c (chan)]
   (future (while true
             (let [[x] (alts!! ins)]
               (>!! c x))))
   c))

(defn fan-out [in cs-or-n]
  (let [cs (if (number? cs-or-n)
             (repeatedly cs-or-n chan)
             cs-or-n)]
   (future (while true
             (let [x (<!! in)
                   outs (map #(vector % x) cs)]
               (alts!! outs))))
   cs))


(defn call-fan
  (let [cout (chan)
        cin (fan-in (fan-out cout (repeatedly 3 chan)))]
    (dotimes [n 10]
      (>!! cout n)
      (prn (<!! cin)))))

(defn -main []
  (println "in core")
  (call-fan))
