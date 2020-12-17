(ns shah.board.square
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.edn :as edn]
   [malli.transform :as mt]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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

(def AlNot
  [:and
   {:title "AlNot"
    :description "A square in algebraic notation"}
   [:re #"^[a-h][1-8]$"]])


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Transformers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def files "abcdefgh")

(defn -square->al-not [[file rank]]
  (str (nth files file) (inc rank)))

(defn -al-not->square [al-not]
  [(->> 0
        (nth al-not)
        (str/index-of files))
   (->> 1
        (nth al-not)
        str
        edn/read-string
        dec)])

(def al-not-transformer
  (mt/transformer
   {:name :al-not
    :decoders {'Square -al-not->square}
    :encoders {'Square -square->al-not}}))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Extractors
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn square->color [[file rank]]
  (if (even? (+ file rank))
    :dark
    :light))

(def al-not->meaning
  (-> "board/square/meaning.edn"
      io/resource
      slurp
      edn/read-string))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Generators
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn all-squares-shuffled []
  (shuffle
   (for [x (range 8)
         y (range 8)]
     [x y])))

(defn gen-square []
  (first (all-squares-shuffled)))

(defn gen-al-not []
  (-square->al-not (gen-square))
  #_(m/encode Square (gen-square) al-not-transformer))

(defn cycle-al-nots []
  (let [state (atom (cycle (all-squares-shuffled)))]
    (fn []
      (let [al-not (-square->al-not (first @state))]
        (swap! state rest)
        al-not))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Playground
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(comment
  (gen-al-not))
