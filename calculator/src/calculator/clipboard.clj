'(def numbers (atom []))

(defn adder [key ref old-state new-state]
  (print "Current sum is " (reduce + new-state)))

(add-watch numbers :adder adder)'

    Uittreksel van: 'Clojure Reactive Programming'. iBooks.

'(swap! numbers conj 1)'

    Uittreksel van: 'Clojure Reactive Programming'. iBooks.