(ns advent.day1
  (:require 
    [clojure.java.io :as io]
    [clojure.string :as str]))

(def sample-input
  "1abc2
  pqr3stu8vwx
  a1b2c3d4e5f
  treb7uchet")

(def sample-input2
  "two1nine
  eightwothree
  abcone2threexyz
  xtwone3four
  4nineeightseven2
  zoneight234
  7pqrstsixteen"
  )

(defn parse-input [input]
  (->> input
       str/split-lines
       (map str/trim)))

(defn extract-numeric-strs [lines]
    (map 
      (fn [string]
      (apply str (filter #(Character/isDigit %) string)))
      lines))

(defn get-calibration-values [numeric-strs]
    (map (fn [nstr]
           (read-string (str/join [(first nstr) (last nstr)])))
         numeric-strs))

(defn solve-part1 [input]
  (->> input
       parse-input
       extract-numeric-strs
       get-calibration-values
       (reduce +)))

(defn replace-digit-words [str]
  ( -> str 
      (str/replace "one" "1")
      (str/replace "two" "2")
      (str/replace "three" "3")
      (str/replace "four" "4")
      (str/replace "five" "5")
      (str/replace "six" "6")
      (str/replace "seven" "7")
      (str/replace "eight" "8")
      (str/replace "nine" "9")))

(defn get-digit-representations [str]
  (remove empty? (flatten (re-seq #"(?=(one|two|three|four|five|six|seven|eight|nine|\d))" str))))

(defn get-calibration-values2 [coll]
  (let [first-and-last [(first coll) (last coll)]
        digits (map replace-digit-words first-and-last)]
    (read-string (str/join digits))))

(defn solve-part2 [input]
  (->> input
       parse-input
       (map get-digit-representations)
       (map get-calibration-values2)
       (reduce +)))

(comment
  (solve-part1 sample-input)
  (solve-part1 (slurp (io/resource "inputDay1.txt"))) ; 54697
  (solve-part2 sample-input2)
  (solve-part2 (slurp (io/resource "inputDay1.txt")))
  (tap> (solve-part2 (slurp (io/resource "inputDay1.txt")))) ; 54697
  )

