(ns stripe-tester-clj.core-test
  (:require [clojure.test :refer :all]
            [stripe-tester-clj.core :refer :all]))

(testing "Sending Data"
  (testing "Send data to webhook URL"))

(testing "Importing Data"
  (testing "Import webhook JSON data to clojure data"
    (testing "Default data"
      (deftest correct-data-type
        (let [data (load-template :invoice-created)]
          (is (instance? clojure.lang.PersistentHashMap data))))
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
                data (load-template :invoice-created custom-data)]
            (is (= (:id data) (:id custom-data))))))
      (testing "Deep nested level"
        (deftest merge-deep-level-custom-data
          (let [custom-data {:data {:object {:lines {:data {:plan {:interval "xxx"}}}}}}
                data (load-template :invoice-created custom-data)
                keys [:data :object :lines :data :plan :interval]]
            (is (= (get-in data keys) (get-in custom-data keys)))))))))
