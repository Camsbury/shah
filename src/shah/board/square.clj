(ns shah.board.square
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]))

(def FILES
  "abcdefgh")

(defn coord->file [coord]
  (nth FILES coord))

(defn file->coord [file]
  (str/index-of FILES file))

(defn coord->rank [coord]
  (inc coord))

(defn rank->coord [rank]
  (dec rank))

(defn coord-pair->square [[x y]]
  (let [file (coord->file x)
        rank (coord->rank y)]
    (str file rank)))

(defn square->coord-pair [square]
  (let [file (nth square 0)
        rank (edn/read-string (str (nth square 1)))]
    [(file->coord file) (rank->coord rank)]))
