(ns calculator.stack
  "Implementation of stack datastructure"
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:refer-clojure :exclude (peek)))

(def stacks (atom {}))

(defn update-stacks
  "Creates a new stack
  Returns: newly created stack"
  [stack id]
  (do
    (swap! stacks assoc id stack)
    (@stacks id))
  (println (@stacks id))
  (list id stack))

(defn create-stack
  "Creates a new stack
  Returns: newly created stack"
  [id]
  (do
   (swap! stacks assoc id ())
   (list id (@stacks id))))

(defn get-stack
  "Returns: stack with id :id"
  [id]
  (or (list id (@stacks id))
      (create-stack id)))

(defn peek
  "Gets top element of the stack
  Params:
    list stack - stack
  Returns: top element of the stack
  Throws: throws error of :type :not-found if stack is empty"
  [[id stack]]
  (or (first stack)
      (throw+ {:type ::not-found :message "stack is empty"})))

(defn push
  "Pushes a number onto a stack
  Params:
    list stack - stack
    int n - number to add to the stack
  Returns: top element of the stack
  Throws: error of :type :invalid if n is empty"
  [[id stack] n]
  (if (and (nil? n) (not (integer? n)))
    (throw+ {:type ::invalid :message "attr is empty or not int"})
    (->
      (conj stack n)
      (update-stacks id)
      (peek))))

(defn pop*
  "Removes the last element of the stack
  Params:
    list s - stack
  Returns: removed element of the stack
  Throws: error of :type :invalid if n is empty"
  [[id stack]]
  (if (empty? stack)
    (throw+ {:type ::not-found :message "can't pop from empty stack"})
    (let [poped-stack (peek (list id stack))]
      (->
       (pop stack)
       (update-stacks id))
      (println poped-stack)
      poped-stack)))
