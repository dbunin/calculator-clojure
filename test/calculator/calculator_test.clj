(ns calculator.calculator-test
  (:require [clojure.test :refer :all]
            [calculator.calculator :as calculator]
            [calculator.stack :as stack])
  (:use [slingshot.slingshot :only [throw+ try+]]))

(deftest execute-func-on-empty-struct
  (testing "Testing that function throws error when struct doesn't have any elements"
    (with-redefs [stack/create-stack (fn [] (atom '()))]
      (with-redefs [stack/pop (fn [a] (throw+ {:type :calculator.stack/not-found :message "can't pop from empty stack"}))]
        (try+
          (calculator/calculate (stack/create-stack) +)
          (catch [:type :calculator.calculator/not-enough-elements] _
            (is 1 1))
          (else (is 1 0)))))))

(deftest execute-calculations
  (testing "Testing that function adds two elements"
    (with-redefs [stack/create-stack (fn [] (atom '(2 2)))]
      (try+
        (let [stack (stack/create-stack)]
          (do
            (is 4 (calculator/calculate stack +))
            (is (atom '(4)) stack))))))
  (testing "Testing that function substracts two elements"
    (with-redefs [stack/create-stack (fn [] (atom '(2 2)))]
      (try+
       (let [stack (stack/create-stack)]
         (do
           (is 0 (calculator/calculate stack -))
           (is (atom '(4)) stack))))))
  (testing "Testing that function multiplies two elements"
    (with-redefs [stack/create-stack (fn [] (atom '(2 2)))]
      (try+
       (let [stack (stack/create-stack)]
         (do
           (is 4 (calculator/calculate stack *))
           (is (atom '(4)) stack))))))
  (testing "Testing that function divides two elements"
    (with-redefs [stack/create-stack (fn [] (atom '(2 2)))]
      (try+
       (let [stack (stack/create-stack)]
         (do
           (is 1 (calculator/calculate stack /))
           (is (atom '(4)) stack)))))))


