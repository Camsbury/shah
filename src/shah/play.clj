(ns shah.play
  (:require
   [clj-telnet.core :as telnet]
   [systemic.core :as systemic :refer [defsys]]))

(def fics-url "freechess.org")
(def fics-port 5000)

(defsys *fics-login*
  (->> "secrets.edn"
       slurp
       read-string
       :fics-login))

(defsys *fics*
  :deps [*fics-login*]
  :start
  (let [fics (telnet/get-telnet fics-url fics-port)]
    (telnet/write fics (:username *fics-login*))
    (telnet/write fics (:password *fics-login*))
    (telnet/read-until fics (str "**** Starting FICS session as " (:username *fics-login*) " ****"))
    (print (telnet/read-all fics))
    fics)
  :stop
  (telnet/kill-telnet *fics*))

(defn write-and-read [cmd]
  (telnet/write *fics* cmd)
  (Thread/sleep 50) ; TODO try until no error, then print (with a max tries)
  (print (telnet/read-all *fics*)))

(defn read-all []
  (print
   (telnet/read-all *fics*)))

(defn play-game [game-id]
  (telnet/write *fics* (str "play" game-id))
  (telnet/write *fics* "style 12"))

(comment
  (systemic/start! `*fics*)
  (systemic/stop! `*fics*)

  (write-and-read "observe 5")
  (write-and-read "style 1")
  (read-all)
  (write-and-read "help command")
  (write-and-read "sought")
  )
