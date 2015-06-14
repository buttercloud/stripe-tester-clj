(ns stripe-tester-clj.core-test
  (:require [clojure.test :refer :all]
            [stripe-tester-clj.core :refer :all]
            [clojure.data.json :as json]
            [clj-http.fake :refer [with-fake-routes]]))

(testing "Sending Data"
  (testing "Send data to webhook URL"
    (let [url "http://localhost:3000/transactions"
          bad-url "http://localhost:3000/fake"
          secure-url "https://localhost:3000/transactions"
          secure-bad-url "https://localhost:3000/fake"
          data (load-template :invoice-created)]
      (with-fake-routes
        {url (fn [req] {:status 200 :headers {} :body (json/write-str data)})
         bad-url (fn [req] {:status 404 :headers {} :body "404"})
         secure-url (fn [req] {:status 200 :headers {} :body (json/write-str data)})
         secure-bad-url (fn [req] {:status 404 :headers {} :body "404"})}
        (testing "create-event should return true when request is successful"
          (is (= true (create-event :invoice-created {:url url}))))
        (testing "create-event should return true when request is successful for secure url"
          (is (= true (create-event :invoice-created {:url secure-url}))))
        (testing "create-event should return response map when request fails"
          (is (map? (create-event :invoice-created {:url bad-url}))))
        (testing "create-event should return response map when request fails for secure url"
          (is (map? (create-event :invoice-created {:url secure-bad-url}))))))))

(testing "Importing Data"
  (testing "Import webhook JSON data to clojure data"

    (testing "Default data"

      (deftest correct-data-type
        (let [data (load-template :invoice-created)]
          (is (map? data))))

      (deftest correct-callback-type-inside-webhook
        (let [callback-type :invoice-created
              data (load-template callback-type)]
          (is (= callback-type (keyword (clojure.string/replace (:type data) #"\." "-"))))))

      (deftest raise-exception-for-invalid-callback-type
        (is (thrown-with-msg? Exception #"Invalid callback type" (load-template :invalid-callback)))))

    (testing "With custom data"
      (testing "Top level"

        (deftest merge-top-level-custom-data
          (let [custom-data {:id "new_id_111"}
                data (load-template :invoice-created {:custom-data custom-data})]
            (is (= (:id data) (:id custom-data))))))

      (testing "Deep nested level"
        (deftest merge-deep-level-custom-data
          (let [custom-data {:data {:object {:lines {:data {:plan {:interval "xxx"}}}}}}
                data (load-template :invoice-created {:custom-data custom-data})
                keys [:data :object :lines :data :plan :interval]]
            (is (= (get-in data keys) (get-in custom-data keys)))))))))
