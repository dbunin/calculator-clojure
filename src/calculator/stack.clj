(ns calculator.stack
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:refer-clojure :exclude (list peek pop)))

(defn create-stack
  "Creates a new stack
  Returns: newly created stack"
  []
  (atom '()))

(defn peek
  "Gets top element of the stack
  Params:
    atom s - stack
  Returns: top element of the stack
  Throws: throws error of :type :not-found if stack is empty"
  [stack]
  (or (first @stack)
      (throw+ {:type ::not-found :message "stack is empty"})))

(defn push
  "Pushes a number onto a stack
  Params:
    atom s - stack
    int n - number to add to the stack
  Returns: top element of the stack
  Throws: error of :type :invalid if n is empty"
  [stack, n]
  (if (and (nil? n) (not (integer? n)))
    (throw+ {:type ::invalid :message "attr is empty or not int"})
    (do
      (swap! stack conj n)
      (peek stack))))

(defn pop
  "Removes the last element of the stack
  Returns: removed element of the stack"
  [stack]
  (if (empty? @stack)
    (throw+ {:type ::not-found :message "can't pop from empty stack"})
    (let [poped-element (peek stack)]
      (reset! stack (rest @stack))
      poped-element)))