(ns shah.registry
  (:require [malli.core :as m]
            [malli.registry :as mr]))

(def registry*
  (atom m/default-registry))

(defn register! [type ?schema]
  (swap! registry* assoc type ?schema))

(comment
  (System/getProperty "malli.registry/type")
  (mr/set-default-registry!
   (mr/mutable-registry registry*)))


