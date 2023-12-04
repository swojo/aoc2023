(ns advent.day2
  (:require 
    [clojure.java.io :as io]
    [clojure.string :as str]))

(def sample-input
  "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
  Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
  Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
  Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
  Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
  )

(defn parse-color-draw
  [[_ count color]]
  [(keyword color) (parse-long count)])

(defn get-game-map
  [line]
  (->> line
       (re-seq #"(\d+)\s(\w+)")
       (map parse-color-draw)
       (group-by first)
       (reduce-kv
         (fn [m k vs]
           (assoc m k (map second vs))) 
         {})
       (reduce-kv
         (fn [m k vs]
           (assoc m k (apply max vs))) 
         {})))

#_(defn get-game-map2
  [line]
  (->> line
       (re-seq #"(\d+)\s(\w+)")
       (map parse-color-draw)
       (group-by first)
       (map (fn [[k vs]]
              [k (map second vs)]))
       (map (fn [[k v]]
              [k (apply max v)]))
       (into {})
       ))

(def constraints 
  {:red 12 
   :green 13
   :blue 14})

(defn satisfies-constraints
  [game-map]
  (apply = true  (map #(<= (get game-map %) (get constraints %)) 
                      (keys constraints))))

(defn solve-part1
  [input]
  ( ->> input
        (str/split-lines)
        (map str/trim)
        (map get-game-map)
        (keep-indexed (fn [idx val] 
                  (when (satisfies-constraints val) (inc idx))))
        (reduce +)
        ))

(defn solve-part2
  [input]
  ( ->> input
        (str/split-lines)
        (map str/trim)
        (mapv get-game-map)
        (map (fn [game]
               (apply * (vals game))))
        (reduce +)))

(comment
  (solve-part1 sample-input)
  (solve-part1 (slurp (io/resource "inputDay2.txt"))) ; 2207
  (solve-part2 sample-input)
  (solve-part2 (slurp (io/resource "inputDay2.txt")))  ; 62241
  )
