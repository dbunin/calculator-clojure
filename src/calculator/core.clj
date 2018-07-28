(ns calculator.core
  "Implementation of REST API for the calculator service"
  (:use compojure.core
            ring.middleware.json-params)
  (:require [clj-json.core :as json]
            [calculator.stack :as stack]
            [calculator.calculator :as calculator])
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:import org.codehaus.jackson.JsonParseException))

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
      (catch NumberFormatException _
        (json-response {"error" "attr should be a number"}))
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
    (json-response (->
                    (Integer. id)
                    (stack/peek))))

  (GET "/calc/:id/push/:n" [id n]
    (json-response (->
                    (Integer. id)
                    (stack/push (Integer. n)))))

  (GET "/calc/:id/pop" [id]
    (json-response (->
                    (Integer. id)
                    (stack/pop*))))

  (GET "/calc/:id/add" [id]
    (json-response (->
                    (Integer. id)
                    (calculator/calculate +))))

  (GET "/calc/:id/subtract" [id]
    (json-response (->
                    (Integer. id)
                    (calculator/calculate -))))

  (GET "/calc/:id/multiply" [id]
    (json-response (->
                    (Integer. id)
                    (calculator/calculate *))))

  (GET "/calc/:id/multiply" [id]
    (json-response (->
                    (Integer. id)
                    (calculator/calculate /)))))

(def app
  (-> handler
      wrap-json-params
      wrap-error-handling))
