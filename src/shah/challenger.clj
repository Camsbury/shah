(ns shah.challenger
  (:require [shah.board.square :as square]))


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

(comment
  (square-meaning-challenge)
  (square-color-challenge))
