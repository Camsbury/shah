(ns shah.board.schema
  (:require
   [shah.board.piece :as piece]
   [shah.board.square :as square]))

(def RankPlacement
  [:and
   [:vector
    [:maybe
     [:map
      [:piece piece/Piece]
      [:color piece/PieceColor]]]]
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
    [:active-color piece/PieceColor]
    [:castling-availability CastlingAvailability]
    [:halfmove-clock [:and int? [:>= 0]]]
    [:fullmove-number [:and int? [:> 0]]]
    [:en-passant-target {:optional true} square/Square]]])
