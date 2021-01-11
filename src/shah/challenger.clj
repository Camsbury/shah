(ns shah.challenger
  (:require [shah.board.square :as square]
            [shah.board.piece :as piece]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Creator
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create-challenge
  [{:keys [question-fn answer-fn]}]
  (let [state (atom nil)]
    (fn []
      (let [{:keys [question]} @state]
        (if question
          (do
            (reset! state nil)
            (answer-fn question))
          (let [question (question-fn)]
            (reset! state {:question question})
            question))))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Generators
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn generate-n-placed-pieces [n]) ; TODO: impl this

(defn naive-n-placed-pieces [n]
  (vec
   (for [_ (range n)]
     [(piece/gen-piece)
      (square/gen-al-not)])))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Challenges
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def square-color-challenge
  "recite colors of squares"
  (create-challenge {:question-fn (square/cycle-al-nots)
                     :answer-fn (comp
                                 square/square->color
                                 square/-al-not->square)}))

(def square-meaning-challenge
  "recite meanings of squares"
  (create-challenge {:question-fn (square/cycle-al-nots)
                     :answer-fn square/al-not->meaning}))

(def square-quadrant-challenge
  "recite the quadrant a square belongs to"
  (create-challenge {:question-fn (square/cycle-al-nots)
                     :answer-fn   (comp
                                   square/square->quadrant
                                   square/-al-not->square)}))

(defn pieces-and-squares-challenge
  "recite the overlapping defended/attacked squares for the given pieces"
  [n]) ; TODO: impl

(comment
  (for [_ (range 3)]
    (naive-n-placed-pieces 3))


  (square-meaning-challenge)
  (square-quadrant-challenge)
  (square-color-challenge))
