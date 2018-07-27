(ns calculator.run
  (:use ring.adapter.jetty)
  (:require [calculator.core :as core]))

(run-jetty #'core/app {:port 8000})