(ns calculator.core
  "Implementation of REST API for the calculator service"
  (:use compojure.core)
  (:use ring.middleware.json-params)
  (:require [clj-json.core :as json]
            [calculator.stack :as stack]
            [calculator.calculator :as calculator])
  (:import org.codehaus.jackson.JsonParseException)
  (:use [slingshot.slingshot :only [throw+ try+]]))

(def stacks (atom {}))

(defn add-stack [id]
  "Adds stack with id :id the list"
  (let [new-attrs (merge (get id) stack/create-stack)]
    (swap! stacks assoc id new-attrs)
    new-attrs))

(defn get [id]
  "Returns the stack with id :id
  If the stack with id :id doesn't exist, creates it"
  (or (@stacks id)
      (@(add-stack id) id)))

(defn json-response [data & [status]]
  "Returns the json messages"
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn wrap-error-handling [handler]
  "Returns error messages if error occurs"
  (fn [req]
    (try+
      (or (handler req)
         (json-response {"error" "resource not found"}))
      (catch JsonParseException e
        (json-response {"error" "malformed json"}))
      (catch [:type :calculator/not-enough-elements] {:keys [message]}
        (json-response {"error" message}))
      (catch [:type :stack/not-found] {:keys [message]}
        (json-response {"error" message}))
      (catch [:type :stack/invalid] {:keys [message]}
        (json-response {"error" message}))
      (catch [:type :stack/invalid] {:keys [message]}
        (json-response {"error" message})))))

(defroutes handler
  "Router for the application"
  (GET "/calc/:id/peek" [id]
    (json-response (stack/peek (get id))))

  (PUT "/calc/:id/push/:n" [id n]
    (json-response (stack/push (get id) n)))

  (DELETE "/calc/:id/pop" [id]
    (json-response (stack/pop (get id))))

  (POST "/calc/:id/add" [id]
    (json-response (calculator/calculate (get id) +)))

  (POST "/calc/:id/subtract" [id]
    (json-response (calculator/calculate (get id) -)))

  (POST "/calc/:id/multiply" [id]
    (json-response (calculator/calculate (get id) *)))

  (POST "/calc/:id/multiply" [id]
    (json-response (calculator/calculate (get id) /))))

(def app
  (-> handler
      wrap-json-params
      wrap-error-handling))
