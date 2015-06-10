# stripe-tester-clj

[![wercker status](https://app.wercker.com/status/71c78aa4a3202a075b26f37e829d3072/s/master "wercker status")](https://app.wercker.com/project/bykey/71c78aa4a3202a075b26f37e829d3072)

stripe-tester-clj is a testing Clojure library used to simulate Stripe webhooks and post them to a specified URL.

## Installation

```clojure
[stripe-tester-clj "0.1.0"]
```

## Usage

In your tests:

```clojure
(ns my-app.core-test
  (:require [stripe-tester-clj.core :as stripe-tester]))
```

### Trigger a webhook event


```clojure
(def webhook-url "http://www.example.com/my-post-url")

;; This will send a HTTP POST request to the URL with the default event data as JSON
;; create-event takes 2 arguments:
;; 1) event type [keyword] (i.e :invoice-created, :customer-created)
;; 2) opts [map]
;;   :url [string] (required)
;;   :api-version [string] (optional, defaults to latest)
;;   :custom-data [map] (optional, will be merged into and overwrite template values)

(stripe-tester/create-event :invoice-created {:url webhook-url})

;; You can specify a specific version of the stripe api to use:

(stripe-tester/create-event :invoice-created {:url webhook-url
                                              :api-version "2013-09-08"})

;; Overwrite template data

(stripe-tester/create-event :invoice-created
                            {:url webhook-url
                             :custom-data {:data
                                           {:object
                                            {:customer "cus_MYCUSTOMERID"}}})

```

### Supported Stripe Webhook API Versions


## License

Copyright © 2015 ButterCloud LLC.

Distributed under the Eclipse Public License version 1.0
