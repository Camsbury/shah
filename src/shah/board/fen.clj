(ns shah.board.fen
  (:require
   [cemerick.url :as url]
   [clojure.java.browse :as browse]))

;;; use malli transformers to make this rock solid!!

(defn fen->lichess-url [fen]
  (->> fen
       url/url-encode
       (str "https://lichess.org/editor?fen=")))

(defn view-fen-on-lichess [fen]
  (->> fen
       fen->lichess-url
       browse/browse-url))

(comment
  (view-fen-on-lichess
   "rnbqkbnr/pp2pppp/2p5/3pP3/3P4/8/PPP2PPP/RNBQKBNR b KQkq - 0 3"))
