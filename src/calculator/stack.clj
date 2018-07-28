(ns calculator.stack
  "Implementation of stack datastructure"
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:refer-clojure :exclude (peek)))

(def stacks (atom {}))

(defn update-stacks
  "Creates a new stack
  Returns: updated stack"
  [stack id]
  (swap! stacks assoc id stack))

(defn create-stack
  "Creates a new stack
  Returns: newly created stack"
  [id]
  (do
   (swap! stacks assoc id ())
   (@stacks id)))

(defn get-stack
  "Returns: stack with id :id"
  [id]
  (or (@stacks id)
      (create-stack id)))

(defn peek
  "Gets top element of the stack
  Params:
    list stack - stack
  Returns: top element of the stack
  Throws: throws error of :type :not-found if stack is empty"
  [id]
  (or (first (get-stack id))
      (throw+ {:type ::not-found :message "stack is empty"})))

(defn push
  "Pushes a number onto a stack
  Params:
    list stack - stack
    int n - number to add to the stack
  Returns: top element of the stack
  Throws: error of :type :invalid if n is empty"
  [id n]
  (let [stack (get-stack id)]
    (if (and (nil? n) (not (integer? n)))
      (throw+ {:type ::invalid :message "attr is empty or not int"})
      (->
        (conj stack n)
        (update-stacks id))
      (peek id))))

(defn pop*
  "Removes the last element of the stack
  Params:
    list s - stack
  Returns: removed element of the stack
  Throws: error of :type :invalid if n is empty"
  [id]
  (let [stack (get-stack id)]
    (if (empty? stack)
      (throw+ {:type ::not-found :message "can't pop from empty stack"})
      (let [poped-stack (peek id)]
        (->
        (pop stack)
        (update-stacks id))
        poped-stack))))
