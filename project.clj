(defproject stripe-tester-clj "0.1.0-SNAPSHOT"
  :description "stripe-tester-clj allows you to submit webhooks to your application without hitting Stripe or requiring connectivity. You can use it in your test suite to simulate webhooks and ensure that your application reacts accordingly."
  :url "https://github.com/buttercloud/stripe-tester-clj"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "1.1.2"]
                 [clj-http-fake "1.0.1"]])
