(ns shah.visualize)

(defn square-color [square]
  (let [[x y] (alg-not->square square)]
    (if (even? (+ x y))
      :dark
      :light)))
