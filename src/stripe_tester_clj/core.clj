(ns stripe-tester-clj.core
  (:require [clojure.data.json :as json]
            [clojure.string :as string]
            [clj-http.client :refer [post success?]]))

(def ^:const latest-version "2015-04-07")

(defn- import-template [callback-type api-version]
  (let [file-name (str "stripe-webhooks/" latest-version "/" (name callback-type) ".json")]
     (if (.exists (clojure.java.io/file file-name))
       (json/read-str (slurp file-name) :key-fn keyword)
       (throw (Exception. "Invalid callback type")))))

(defn load-template
  ([callback-type]
   (import-template callback-type latest-version))
  ([callback-type {:keys [api-version custom-data]
                   :or {api-version latest-version}
                   :as opts}]
   (let [data (import-template callback-type api-version)]
     (merge data custom-data))))

(defn create-event
  [callback-type {:keys [url api-version custom-data]
                  :or {api-version latest-version}
                  :as opts}]
  (assert (not (string/blank? url)) "You must pass a valid :url in opts")
  (let [data (load-template callback-type
                              (select-keys opts [api-version custom-data]))
          response (post url {:body data
                              :content-type :json
                              :throw-exceptions false})]
    (if (success? response)
      true
      response)))
