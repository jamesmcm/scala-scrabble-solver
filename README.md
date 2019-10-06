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
sbt "run gamemode board.txt wordlist.txt letters_string"
```

Where gamemode is one of:

* wwfmp - Words With Friends full multiplayer board (15x15)
* wwfsp - Words With Friends Singleplayer/Facebook board (11x11)
* scrabble - Scrabble full board (15x15)


e.g.

```bash
sbt "run {wwfmp,wwfsp,scrabble} ./src/test/data/simple.txt ./data/wwfwordlist.txt LETTERS"
```

### Run unit tests:

```bash
sbt test
```

## Input

* The board should be a square
* Empty tiles are represented by -
* Tiles placed as blank/wildcard tiles should be followed by \* i.e. B\*
* See examples in src/test/data/ directory

## Output

Output is a printed list of strings sorted in descending order of score:
```
((Position ((row, column) starting from 0), Direction, Word), Word Length, Score)
```

## TODO

* Profile to improve run speed
* Parallelise execution by using Seqs instead of Lists
