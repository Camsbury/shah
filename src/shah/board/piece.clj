(ns shah.board.piece
  (:require
   [malli.generator :as mg]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def PieceColor
  [:and
   {:title "PieceColor"
    :description "The color of the chess piece"}
   [:enum :white :black]])

(def Piece
  [:and
   {:title "Piece"
    :description "A chess piece"}
   [:enum :pawn :knight :bishop :rook :queen :king]])


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Generators
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn gen-piece []
  (mg/generate Piece {:size 100}))

(def starting-pieces nil) ; TODO: pairs of piece and color

(defn random-starting-pieces
  "Take n pieces from a shuffled assortment of starting pieces"
  [n]
  (take n (shuffle starting-pieces)))
