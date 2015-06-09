(ns stripe-tester-clj.core
  (:require [clojure.data.json :as json]))

(def latest-stripe-version "2015-04-07")

(defn file-exists?
  [file-name]
  (.exists (clojure.java.io/file file-name)))

(defn load-template
  ([callback-type]
   (let [file-name (str "stripe-webhooks/" stripe-version "/" (name callback-type) ".json")]
     (if (file-exists? file-name)
       (json/read-str (slurp file-name) :key-fn keyword)
       (throw (Exception. "Invalid callback type")))))

  ([callback-type custom-data]
   (let [data (load-template callback-type)]
     (merge data custom-data))))

(defn post-to-url
  [data])

(defn create-event
  ([callback-type]
   (let [data (load-template callback-type)]
     (post-to-url data)))
  ([callback-type custom-data]
   (let [data (load-template callback-type custom-data)]
     (post-to-url data))))
