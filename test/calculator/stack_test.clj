(ns calculator.stack-test
  (:require [clojure.test :refer :all]
            [calculator.stack :as stack])
  (:use [slingshot.slingshot :only [throw+ try+]]))

(deftest peek-epmty-stack-test
  (testing "Testing that peeking epmty stack throws exception"
    (try+
    (stack/peek (stack/create-stack))
    (catch [:type :calculator.stack/not-found] _
      (is 1 1))
    (else (is 1 0)))))

(deftest push-empty-attr-test
  (testing "Testing throwing exception when giving empty attribute"
    (try+
      (stack/push (stack/create-stack) nil)
      (catch [:type :calculator.stack/invalid] _
        (is 1 1))
      (else (is 1 0)))))

(deftest push-test
  (let [stack (stack/create-stack)]
    (testing "Testing that push returns the pushed element"
      (is 1 (stack/push stack 1)))
    (testing "Testing that peek after push returns pushed element"
      (is 1 (stack/peek stack)))))

(deftest pop
  (let [stack (stack/create-stack)]
    (testing "Testing pop empty stack"
      (try+
       (stack/pop stack)
       (catch [:type :calculator.stack/not-found] _
         (is 1 1))
       (else (is 1 0))))
    (testing "Testing that pop returns the top element and deletes it"
      (do
        (stack/push stack 1)
        (stack/push stack 2)
        (is 2 (stack/pop stack))
        (is 1 (stack/peek stack))))))
