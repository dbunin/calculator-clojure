(ns calculator.stack-test
  (:require [clojure.test :refer :all]
            [calculator.stack :as stack])
  (:use [slingshot.slingshot :only [throw+ try+]]))

(deftest peek-epmty-stack-test
  (testing "Testing that peeking epmty stack throws exception"
    (try+
      (stack/peek 0)
      (catch [:type :calculator.stack/not-found] _
        (is 1 1))
      (else (is 1 0)))))

(deftest push-empty-attr-test
  (testing "Testing throwing exception when giving empty attribute"
    (try+
      (stack/push 0 nil)
      (catch [:type :calculator.stack/invalid] _
        (is 1 1))
      (else (is 1 0)))))

(deftest push-test
  (testing "Testing that push returns the pushed element"
    (is 1 (stack/push 0 1)))
  (testing "Testing that peek after push returns pushed element"
    (is 1 (stack/peek 0))))

(deftest pop*-test
  (testing "Testing pop empty stack"
    (try+
      (stack/pop* 1)
      (catch [:type :calculator.stack/not-found] _
        (is 1 1))
      (else (is 1 0))))
  (testing "Testing that pop returns the top element and deletes it"
    (do
      (stack/push 1 1)
      (stack/push 1 2)
      (is 2 (stack/pop* 1))
      (is 1 (stack/peek 1)))))
