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
  {color (read-string count)})

(defn get-all-draws
  [line]
  (->> line
       (re-seq #"(\d+)\s(\w+)")
       (map parse-color-draw)))

(defn get-max-per-color
  [draw-maps-coll color]
  (let [draw-values (remove nil? (map #(get % color) draw-maps-coll))]
    (if (empty? draw-values)
      0
      (apply max (remove nil? (map #(get % color) draw-maps-coll))))))

(def colors '("blue" "red" "green"))
(def constraints 
  {"red" 12 
   "green" 13
   "blue" 14})

(defn get-game-map 
  [line]
  (let [draws (get-all-draws line)]
    (apply merge (map (fn [color]
         (hash-map color (get-max-per-color draws color)))
         colors))))

(defn satisfies-constraints
  [game-map]
  (apply = true  (map #(<= (get game-map %) (get constraints %)) colors))
  )

(defn solve-part1
  [input]
  ( ->> input
        (str/split-lines)
        (map str/trim)
        (mapv get-game-map)
        (keep-indexed (fn [idx val] 
                  (if (satisfies-constraints val) (inc idx))))
        (reduce +)))

(comment
  (solve-part1 sample-input)
  (solve-part1 (slurp (io/resource "inputDay2.txt"))) ; 54697

  )
