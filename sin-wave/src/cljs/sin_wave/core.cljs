(ns sin-wave.core)

;(defonce app-state (atom {:text "Hello Chestnut!"}))

(defn main [])

(def canvas (.getElementById js/document "myCanvas"))
(def ctx    (.getContext canvas "2d"))

;; Clear canvas before doing anything else
(.clearRect ctx 0 0 (.-width canvas) (.-height canvas))
(def interval   js/Rx.Observable.interval)
(def time       (interval 10))

#_(-> time
    (.take 6)
    (.subscribe (fn [n]
                  (.log js/console n))))

(defn deg-to-rad [n]
  (* (/ Math/PI 180) n))

(defn sine-coord [x]
  (let [sin (Math/sin (deg-to-rad x))
        y   (- 100 (* sin 90))]
    {:x   x
     :y   y
     :sin sin}))

(def sine-wave
  (.map time sine-coord))

(defn fill-rect [x y colour]
  (set! (.-fillStyle ctx) colour)
  (.fillRect ctx x y 2 2))

#_(-> sine-wave
    (.take 600)
    (.subscribe (fn [{:keys [x y]}]
                  (fill-rect x y "orange"))))

(def colour (.map sine-wave
                  (fn [{:keys [sin]}]
                    (if (< sin 0)
                      "red"
                      "blue"))))

#_(-> (.zip sine-wave colour #(vector % %2))
    (.take 600)
    (.subscribe (fn [[{:keys [x y]} colour]]
                  (fill-rect x y colour))))

(def red  (.map time (fn [_] "red")))
(def blue (.map time (fn [_] "blue")))

(def rxconcat     js/Rx.Observable.concat)
(def defer      js/Rx.Observable.defer)
(def from-event js/Rx.Observable.fromEvent)

(def mouse-click (from-event canvas "click"))

(def cycle-colour
  (rxconcat (.takeUntil red mouse-click)
          (defer #(rxconcat (.takeUntil blue mouse-click)
                          cycle-colour))))

(-> (.zip sine-wave cycle-colour #(vector % %2))
    (.take 600)
    (.subscribe (fn [[{:keys [x y]} colour]]
                  (fill-rect x y colour))))