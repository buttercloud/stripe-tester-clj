# stripe-tester-clj

[![wercker status](https://app.wercker.com/status/71c78aa4a3202a075b26f37e829d3072/s/master "wercker status")](https://app.wercker.com/project/bykey/71c78aa4a3202a075b26f37e829d3072)

stripe-tester-clj is a testing Clojure library used to simulate Stripe webhooks and post them to a specified URL.

stripe-tester-clj allows you to submit webhooks to your application without hitting Stripe or requiring connectivity. You can use it in your test suite to simulate webhooks and ensure that your application reacts accordingly.


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

(stripe-tester/create-event :invoice-created {:url webhook-url})

;; This will send a HTTP POST request to the URL with the default event data as JSON
;; create-event takes 2 arguments:
;; 1) event type [keyword] (i.e :invoice-created, :customer-created)
;; 2) opts [map]
;;   :url [string] (required)
;;   :api-version [string] (optional, defaults to latest)
;;   :custom-data [map] (optional, will be merged into and overwrite template values)

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

## Supported Stripe Webhook API Versions

* [2015-04-07](https://github.com/buttercloud/stripe-tester-clj/tree/master/stripe-webhooks/2015-04-07)


## Contributing

* Fork it
* Create your feature branch
* Add your changes, and add a test for the changes.
* Run tests using

```bash
  $ lein test
```
* Make sure everything is passing
* Push the branch
* Create a new Pull Request

## License

Copyright (c) 2015 ButterCloud LLC.

MIT License

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
