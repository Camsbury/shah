(ns shah.board.square.schema
  (:require
   [malli.generator :as mg]))

(def Coordinate
  [:and
   {:title "Coordinate"
    :description "A coordinate on a single axis"}
   [:int {:min 0 :max 7}]])

(def Square
  [:and
   {:title "Square"
    :description "A square on the board"}
   [:tuple
    Coordinate
    Coordinate]])

(defn all-squares-shuffled []
  (shuffle
   (for [x (range 8)
         y (range 8)]
     [x y])))

(def gen-square
  (first (all-squares-shuffled)))
