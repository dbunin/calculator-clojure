(ns calculator.calculator
  "Implementation of stack calculator object"
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:require [calculator.stack :as stack]))

(defn calculate
  "Removes the top and top-1 from the stack and replaces it with (calc-function stack[top-1] stack[top])
  Params:
    atom stack - stack
    function calc-function - function to be executed on top two numbers of stack
  Returns: result of executed given function
  Throws: throws error of :type :not-enough-elements if stack is doesn't include 2 elements"
  [id calc-function]
  (try+
    (let [sum (calc-function (stack/pop* (stack/get-stack id)) (stack/pop* (stack/get-stack id)))]
      (->
       (stack/get-stack id)
       (stack/push sum))
      sum)
   (catch [:type :calculator.stack/not-found] _
     (throw+ {:type ::not-enough-elements :message "Stack doesn't have two top elements"}))))
