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
    Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11")

(defn parse-line
  [line]
  (let [grouped (str/split line #":|\|")
        extracted-nums (map #(re-seq #"\d+" %) grouped)
        [card-num winning found] (map
                                  (fn [seq-of-num-strs]
                                    (map #(parse-long %) seq-of-num-strs))
                                  extracted-nums)]
    {:card-num (first card-num)
     :winning winning
     :found found}))

(defn parse-input
  [in]
  (->> in
       str/split-lines
       (map parse-line)))

(defn add-match-count
  [{:keys [winning found] :as m}]
  (let [intersection (set/intersection (into #{} winning) (into #{} found))]
    (assoc m :match-count (count intersection))))

(defn solve-part1
  [input]
  (->> input
       parse-input
       (map add-match-count)
       (map :match-count)
       (remove zero?)
       (map #(long (math/pow 2 (dec %))))
       (reduce + 0)))

(comment
  (solve-part1 sample-input)
  (solve-part1 (slurp (io/resource "inputDay4.txt")))
  )

(defn add-cards-to-copy
  [{:keys [card-num match-count] :as m}]
  (let [next-idx (inc card-num)]
    (if (> match-count 0)
      (assoc m :cards-to-copy (range next-idx (+ next-idx match-count)))
      m)))

(defn add-card-copies
  [seq-of-maps]
  (reduce
   (fn [total {:keys [card-num match-count cards-to-copy] :as m}]
     (let [curr-card-count (get total card-num)]
       (reduce
        (fn [total card]
          (update total card + curr-card-count))
        total
        cards-to-copy)))
   (zipmap (map :card-num seq-of-maps) (repeat 1))
   seq-of-maps))

(defn solve-part2
  [input]
  (->> input
       parse-input
       (map add-match-count)
       (map add-cards-to-copy)
       (sort-by :card-num)
       (add-card-copies)
       (vals)
       (reduce + 0)
       ))

(comment
  (solve-part2 sample-input)
  (solve-part2 (slurp (io/resource "inputDay4.txt")))
  )

