(ns shah.registry
  (:require [malli.core :as m]
            [malli.registry :as mr]))

(def registry*
  (atom (m/default-schemas)))

(defn register! [type ?schema]
  (swap! registry* assoc type ?schema))

(mr/set-default-registry!
 (mr/mutable-registry registry*))


