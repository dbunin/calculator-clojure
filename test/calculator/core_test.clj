(ns calculator.core-test
  (:use clojure.test
        ring.mock.request
        calculator.core))

(deftest scenario-one-test
  (testing "not-found route"
    (let [response (app (request :get "/random-link"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:body]) "{\"error\":\"resource not found\"}"))))
  (testing "peek empty stack"
    (let [response (app (request :get "/calc/1/peek"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "{\"error\":\"stack is empty\"}"))))
  (testing "push and then pop"
    (let [response (app (request :get "/calc/1/push/1"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "1")))
    (let [response (app (request :get "/calc/1/pop"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "1")))
    (let [response (app (request :get "/calc/1/peek"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "{\"error\":\"stack is empty\"}"))))
  (testing "push and then peek"
    (let [response (app (request :get "/calc/1/push/1"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "1")))
    (let [response (app (request :get "/calc/1/peek"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "1"))))
  (testing "test addition"
    (let [response (app (request :get "/calc/1/push/1"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "1")))
    (let [response (app (request :get "/calc/1/add"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "2")))
    (let [response (app (request :get "/calc/1/peek"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "2"))))
  (testing "test multiplication"
    (let [response (app (request :get "/calc/1/push/4"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "4")))
    (let [response (app (request :get "/calc/1/multiply"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "8")))
    (let [response (app (request :get "/calc/1/peek"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (get-in response [:body]) "8")))))