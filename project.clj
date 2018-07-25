(defproject calculator "0.0.1"
  :description "REST interface for stack calculator"
  :dependencies
    [[org.clojure/clojure "1.8.0"]
     [slingshot "0.12.2"]
     [ring/ring-jetty-adapter "1.7.0-RC1"]
     [ring-json-params "0.1.3"]
     [compojure "0.4.0"]
     [clj-json "0.5.3"]]
  :dev-dependencies
    [[lein-run "1.0.0-SNAPSHOT"]]
  :main calculator.run)
