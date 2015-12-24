(ns advent.core
  (:require [clojure.java.io :as io]
            [clojure.repl :refer :all]
            [clojure.string :as str]))

(defn slurp-resource [file-name]
  (slurp (io/resource file-name)))

(defn entered-basement? [found-index i sum]
  (and (> found-index i)
       (< sum 0)))

(defn accumulate-sum-index [{:keys [sum found-index]} [i n]]
  (let [sum  (+ sum n)]
    {:sum sum
     :found-index (if (entered-basement? found-index i sum)
                    i found-index)}))

(defn count-parens [parens]
  (->> parens
      seq
      (map #(case % \( 1 \) -1 0))
      (map vector (map inc (range)))
      (reduce accumulate-sum-index
              {:sum 0 :found-index 36rzzz})))

(defn day-1 [file-name]
  (-> file-name
      slurp-resource
      count-parens))

(defn split-specs [box-spec]
  (->> (str/split box-spec #"x" 3)
       (map #(Integer/parseInt %))
       sort))

(defn area [l w]
  (* l w))

(defn box-area+ [[l w h]]
  (+ (* 2 (+ (area l w)
             (area w h)
             (area l h)))
     (area l w)))

(defn ribbon-length+ [[l w h]]
  (+ (* 2 (+ l w))
     (* l w h)))

(defn day-2 [file-name]
  (->> file-name
       io/resource
       io/reader
       line-seq
       (map (comp (juxt box-area+ ribbon-length+) split-specs))
       (reduce #(map + %1 %2) [0 0])))
