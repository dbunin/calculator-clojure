(ns calculator.core
  "Implementation of REST API for the calculator service"
  (:use compojure.core
            ring.middleware.json-params)
  (:require [clj-json.core :as json]
            [calculator.stack :as stack]
            [calculator.calculator :as calculator])
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:import org.codehaus.jackson.JsonParseException))

(def stacks (list (stack/create-stack) (stack/create-stack) (stack/create-stack)))

(defn add-stack
  "Adds stack with id :id the list"
  [id]
  (let [new-stack (merge (@stacks id) (stack/create-stack))]
    (swap! stacks assoc id new-stack)
    new-stack))

(defn get-stack
  "Returns the stack with id :id
  If the stack with id :id doesn't exist, creates it"
  [id]
  (nth stacks id))

(defn json-response
  "Returns the json messages"
  [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn wrap-error-handling
  "Returns error messages if error occurs"
  [handler]
  (fn [req]
    (try+
      (or (handler req)
         (json-response {"error" "resource not found"}))
      (catch JsonParseException e
        (json-response {"error" "malformed json"}))
      (catch IndexOutOfBoundsException _
        (json-response {"error" "There is no stack with such id"}))
      (catch [:type :calculator.calculator/not-enough-elements] {:keys [message]}
        (json-response {"error" message}))
      (catch [:type :calculator.stack/not-found] {:keys [message]}
        (json-response {"error" message}))
      (catch [:type :calculator.stack/invalid] {:keys [message]}
        (json-response {"error" message}))
      (catch [:type :calculator.stack/invalid] {:keys [message]}
        (json-response {"error" message})))))

(defroutes handler
  "Router for the application"
  (GET "/calc/:id/peek" [id]
    (json-response (stack/peek (get-stack (Integer. id)))))

  (GET "/calc/:id/push/:n" [id n]
    (json-response (stack/push (get-stack (Integer. id)) (Integer. n))))

  (GET "/calc/:id/pop" [id]
    (json-response (stack/pop (get-stack (Integer. id)))))

  (GET "/calc/:id/add" [id]
    (json-response (calculator/calculate (get-stack (Integer. id)) +)))

  (GET "/calc/:id/subtract" [id]
    (json-response (calculator/calculate (get-stack (Integer. id)) -)))

  (GET "/calc/:id/multiply" [id]
    (json-response (calculator/calculate (get-stack (Integer. id)) *)))

  (GET "/calc/:id/multiply" [id]
    (json-response (calculator/calculate (get-stack (Integer. id)) /))))

(def app
  (-> handler
      wrap-json-params
      wrap-error-handling))
