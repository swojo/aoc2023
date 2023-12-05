(ns advent.day4
(:require 
  [clojure.java.io :as io]
  [clojure.string :as str]
  [clojure.set :as set]
  [clojure.math :as math]))

(def sample-input
    "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
    Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
    Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
    Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
    Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
    )

(defn parse-list
  [list-str]
  (let [num-strs (-> list-str
       str/trim
       (str/split #"\s+"))]
    (set (map parse-long num-strs))))

(defn parse-line
  [line]
  (let [matches  (first (re-seq #"([\d+\s+]+)[|]\s+([\d+\s+]+)" line))
        list-strs [(second matches) (last matches)]]
    (map parse-list list-strs)))

(defn card-value
  [line]
  (let [sets (parse-line line)
        matches-count (count (apply set/intersection sets))]
    (if (<= matches-count 0)
      0
      (int (math/pow 2 (- matches-count 1))))))

(defn solve-part1
  [sample-input]
  (->> sample-input
    str/split-lines
    (map card-value)
    (reduce +)))

(comment
  (solve-part1 sample-input)
  (solve-part1 (slurp (io/resource "inputDay4.txt"))) ; 2207

  )

; parse into winning + actual lists
; put in set
; count

