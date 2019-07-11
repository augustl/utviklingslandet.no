(ns utviklingslandet-no.web
  (:require [stasis.core :as stasis]
            [optimus.assets :as assets]
            [optimus.prime :as optimus]
            [optimus.strategies :as strategies]
            [optimus.optimizations :as optimizations]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [hiccup.page]
            [prone.middleware :as prone]
            [utviklingslandet-no.rss :as rss]))

(defn get-home-page []
  (hiccup.page/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title "Utviklingslandet"]
     [:link {:type "application/rss+xml" :rel "alternate" :title "utviklingslandet.no" :href "https://utviklingslandet.no/rss.xml"}]]
    [:body
     "Bør vi lage en web egentlig? Vi er ikke sikre, derfor har du fått denne stygge redirecten til backblaze sin b2 bucket."]))

(defn get-pages []
  {"/" (get-home-page)
   "/rss.xml" (rss/generate-rss (-> "episodes.edn" clojure.java.io/resource slurp clojure.edn/read-string))})

(defn get-assets []
  (assets/load-assets "public" [#".*"]))


(def app (-> (stasis/serve-pages get-pages)
             (optimus/wrap get-assets optimizations/none strategies/serve-live-assets)
             wrap-content-type
             prone/wrap-exceptions))
