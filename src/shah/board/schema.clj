(ns shah.board.schema
  (:require [malli.core :as m]
            [shah.board.square.schema :as square]))

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

(def RankPlacement
  [:and
   [:vector
    [:maybe
     [:map
      [:piece Piece]
      [:color PieceColor]]]]
   [:fn #(= 8 (count %))]])

(def PiecePlacement
  [:and
   [:vector RankPlacement]
   [:fn #(= 8 (count %))]])

(def CastlingSide
  [:enum :queenside :kingside])

(def CastlingAvailability
  [:map
   [:white
    [:set CastlingSide]
    :black
    [:set CastlingSide]]])

(def Board
  [:and
   {:title "Board"
    :description "Chessboard state isomorphic to FEN"}
   [:map
    [:piece-placement PiecePlacement]
    [:active-color PieceColor]
    [:castling-availability CastlingAvailability]
    [:halfmove-clock [:and int? [:>= 0]]]
    [:fullmove-number [:and int? [:> 0]]]
    [:en-passant-target {:optional true} square/Square]]])
