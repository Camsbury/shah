(ns shah.board.init
  (:require [malli.core :as m]
            [shah.board.schema :as schema]))

(def pawn-rank
  (vec
   (for [_ (range 8)]
     :pawn)))

(def init-back-rank
  [:rook :knight :bishop :queen
   :king :bishop :knight :rook])

(def empty-rank
  (vec
   (for [_ (range 8)]
     nil)))

(defn color-rank [color rank]
  (mapv
   (fn [piece]
     {:color color
      :piece piece})
   rank))

(def starting-board
  {:piece-placement
   [(color-rank :white init-back-rank)
    (color-rank :white pawn-rank)
    empty-rank
    empty-rank
    empty-rank
    empty-rank
    (color-rank :black pawn-rank)
    (color-rank :black init-back-rank)]

   ;who is next to move
   :active-color :white

   ;who can castle and where
   :castling-availability
   {:white #{:kingside :queenside}
    :black #{:kingside :queenside}}

   ;moves since a capture or pawn advance
   :halfmove-clock 0

   ;number of full moves played (starting at 1)
   :fullmove-number 1})

(assert
 (m/validate schema/Board starting-board))

