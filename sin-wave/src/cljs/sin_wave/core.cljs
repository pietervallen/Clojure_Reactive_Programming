(ns sin-wave.core)

(defonce app-state (atom {:text "Hello Chestnut!"}))

(defn main [])

(def canvas (.getElementById js/document "myCanvas"))
(def ctx    (.getContext canvas "2d"))

;; Clear canvas before doing anything else
(.clearRect ctx 0 0 (.-width canvas) (.-height canvas))

(def interval   js/Rx.Observable.interval)
(def time       (interval 10))

(-> time
    (.take 5)
    (.subscribe (fn [n]
                  (.log js/console n))))
