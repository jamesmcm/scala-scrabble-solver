# scala-scrabble-solver

Solver for Words With Friends and Scrabble, written in Scala, using a greedy brute-force approach.

Note: Only tested with Words With Friends

## Rules

Words With Friends: https://www.zyngawithfriends.com/wordswithfriends/support/WWF\_Rulebook.html

Scrabble: https://scrabble.hasbro.com/en-us/rules

## Usage

### Build:

```bash
sbt compile
```

### Run:

```bash
sbt "run gamemode board.txt wordlist.txt letters_string (limit)"
```

Where gamemode is one of:

* wwfmp - Words With Friends full multiplayer board (15x15)
* wwfsp - Words With Friends Singleplayer/Facebook board (11x11)
* scrabble - Scrabble full board (15x15)

And `(limit)` is an optional integer argument for the number of results to return.

#### Example

```bash
sbt "run {wwfmp,wwfsp,scrabble} ./src/test/data/simple.txt ./data/wwfwordlist.txt LETTERS"

(((1,9),Vertical,STERLET),7,66)
(((1,9),Vertical,LETTERS),7,62)
(((1,9),Vertical,TRESTLE),7,62)
(((7,9),Vertical,SETTLER),7,62)
(((7,9),Vertical,STERLET),7,62)
```

The given word starting position has the row number first (starting from 0), so `(0, 0)` is the top-left tile, and `(1, 0)` is the tile below that.

i.e. in this case we have the letters: `L,E,T,T,E,R,S` and we use the Words With Friends wordlist.

Our initial board (15x15) is:

```
---------------
---------------
---------------
---------------
---------------
---------------
---------------
------BAT------
---------------
---------------
---------------
---------------
---------------
---------------
---------------
```

And the best move is to play STERLET vertically on 1 row down, 9 tiles to the right - to earn 66 points:

```
---------------
--------S------
--------T------
--------E------
--------R------
--------L------
--------E------
------BAT------
---------------
---------------
---------------
---------------
---------------
---------------
---------------
```

Note a sterlet is defined as:

> A small sturgeon of the Danube basin and Caspian Sea area, farmed and commercially fished for its flesh and caviar.

### Run unit tests:

```bash
sbt test
```

## Input

* The board should be a square
* Empty tiles are represented by -
* A wildcard in the letters available is represented by a \*
* Tiles placed as blank/wildcard tiles should be followed by \* i.e. B\* - this is important for correct scoring.
* See examples in src/test/data/ directory

## Output

Output is a printed list of strings sorted in descending order of score:
```
((Position ((row, column) starting from 0), Direction, Word), Word Length, Score)
```

## TODO

* Profile to improve run speed
* Parallelise execution by using Seqs instead of Lists
* Remove cats dependency
