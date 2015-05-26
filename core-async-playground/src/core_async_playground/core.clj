(ns core-async-playground.core)

(defn now [] (new java.util.Date))

(now)

(import 'java.util.concurrent.ArrayBlockingQueue)

(defn producer [c]
  (prn (now) "Taking a nap")
  (Thread/sleep 5000)
  (prn (now) "Now putting a name in queue...")
  (.put c "Leo"))

(defn consumer [c]
  (prn (now) "Attempting to take value from queue now...")
  (prn (now) (str "Got it. Hello " (.take c) "!")))

(def chan (ArrayBlockingQueue. 10))

(future (consumer chan))
(future (producer chan))
