(ns shah.play.parse
  (:require
   [clojure.string :as str]
   [instaparse.core :as insta]))

(def line-parse-def
  (let [rows (repeat 8 "ROW")]
    (str/join
     " "
     (flatten
      ["LINE: '<12> '"
       rows
       "PLAYER"
       ;; * -1 if the previous move was NOT a double pawn push, otherwise the chess
       ;;   board file  (numbered 0--7 for a--h) in which the double push was made
       "DPP"
       ;; * can White still castle short? (0=no, 1=yes)
       "WCS"
       ;; * can White still castle long?
       "WCL"
       ;; * can Black still castle short?
       "BCS"
       ;; * can Black still castle long?
       "BCL"
       ;; * the number of moves made since the last irreversible move.  (0 if last move
       ;;   was irreversible.  If the value is >= 100, the game can be declared a draw
       ;;   due to the 50 move rule.)
       "HLV"
       ;; * The game number
       "GNUM"
       ;; * White's name
       "WNAME"
       ;; * Black's name
       "BNAME"
       ;; * my relation to this game:
       ;;     -3 isolated position, such as for "ref 3" or the "sposition" command
       ;;     -2 I am observing game being examined
       ;;      2 I am the examiner of this game
       ;;     -1 I am playing, it is my opponent's move
       ;;      1 I am playing and it is my move
       ;;      0 I am observing a game being played
       "ME"
       ;; * initial time (in seconds) of the match
       "INITTIME"
       ;; * increment In seconds) of the match
       "INCTIME"
       ;; * White material strength
       "WMAT"
       ;; * Black material strength
       "BMAT"
       ;; * White's remaining time
       "WREMTIME"
       ;; * Black's remaining time
       "BREMTIME"
       ;; * the number of the move about to be made (standard chess numbering -- White's
       ;;   and Black's first moves are both 1, etc.)
       "CURRMOVE"
       ;; * verbose coordinate notation for the previous move ("none" if there were
       ;;   none) [note this used to be broken for examined games]
       "VERBNOT"
       ;; * time taken to make previous move "(min:sec)".
       "TIMETAKEN"
       ;; * pretty notation for the previous move ("none" if there is none)
       "SHORTNOT"
       ;; * flip field for board orientation: 1 = Black at bottom, 0 = White at bottom.
       "FLIPPED"
       "EXTRA"]))))


;; TODO: insta.transform this into something that is easy to use (could accrue in a game atom)
(def parse-move-lines
  (insta/parser
   (str/join
    "\n"
    [line-parse-def
     "PLAYER:  #'[WB]' SPC"
     "ROW:  #'[-prnbkqPRNBKQ]{8}' SPC"
     "DPP:  #'-{0,1}\\d' SPC"
     "WCS: BOOL SPC"
     "WCL: BOOL SPC"
     "BCS: BOOL SPC"
     "BCL: BOOL SPC"
     "HLV: #'\\d+' SPC"
     "GNUM: #'\\d+' SPC"
     "WNAME: #'\\S+' SPC"
     "BNAME: #'\\S+' SPC"
     "ME: #'-{0,1}\\d' SPC"
     "INITTIME: #'\\d+' SPC"
     "INCTIME: #'\\d+' SPC"
     "WMAT: #'\\d+' SPC"
     "BMAT: #'\\d+' SPC"
     "WREMTIME: #'\\d+' SPC"
     "BREMTIME: #'\\d+' SPC"
     "CURRMOVE: #'\\d+' SPC"
     "VERBNOT: #'\\S+' SPC"
     "TIMETAKEN: '(' #'\\d+' ':' #'\\d+' ')' SPC"
     "SHORTNOT: #'\\S+' SPC"
     "FLIPPED: BOOL"
     "EXTRA: #'.*'"
     "BOOL: '0' | '1'"
     "SPC: ' '"])))

(def example-move-line
  "<12> -r-----k p-----pp -------- ---Q---- ---PP--- --K-R--- Pr-----P ---q---- W -1 0 0 0 0 6 5 chessSlicer GuestMZNT 0 5 0 18 22 17 9 33 Q/g4-d1 (0:04) Qd1 0 1 0")

(comment
  (insta/parse parse-move-lines example-move-line))
