(ns calculator.calculator-test
  (:require [clojure.test :refer :all]
            [calculator.calculator :as calculator]
            [calculator.stack :as stack])
  (:use [slingshot.slingshot :only [throw+ try+]]))

(deftest execute-func-on-empty-struct
  (testing "Testing that function throws error when struct doesn't have any elements"
    (with-redefs [stack/pop* (fn [a] (throw+ {:type :calculator.stack/not-found :message "can't pop from empty stack"}))]
      (try+
        (calculator/calculate 1 +)
        (catch [:type :calculator.calculator/not-enough-elements] _
          (is 1 1))
        (else (is 1 0))))))

(deftest execute-calculations
  (with-redefs [stack/push (fn [a b] 1)]
    (testing "Testing that function adds two elements"
      (with-redefs [stack/pop* (fn [a] 2)]
        (try+
        (is 4 (calculator/calculate 1 +)))))
            
    (testing "Testing that function substracts two elements"
      (with-redefs [stack/pop* (fn [a] 2)]
        (try+
          (is 0 (calculator/calculate 1 -)))))

    (testing "Testing that function multiplies two elements"
      (with-redefs [stack/pop* (fn [a] 3)]
        (try+
          (is 9 (calculator/calculate 1 *)))))
    
    (testing "Testing that function divides two elements"
      (with-redefs [stack/pop* (fn [a] 2)]
        (try+
          (is 1 (calculator/calculate 1 /)))))))


