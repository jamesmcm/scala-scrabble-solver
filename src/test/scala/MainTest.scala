// Copyright (C) 2019 James McMurray
//
// scala-sudoku-solver is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// scala-sudoku-solver is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with scala-sudoku-solver.  If not, see <http://www.gnu.org/licenses/>.

import org.scalatest.FunSuite

class MainTest extends FunSuite {
  val letterScores: Map[Char, Int] = Map(
    'A' -> 1,
    'B' -> 4,
    '*' -> 0,
    'C' -> 4,
    'D' -> 2,
    'E' -> 1,
    'F' -> 4,
    'G' -> 3,
    'H' -> 3,
    'I' -> 1,
    'J' -> 10,
    'K' -> 5,
    'L' -> 2,
    'M' -> 4,
    'N' -> 2,
    'O' -> 1,
    'P' -> 4,
    'Q' -> 10,
    'R' -> 1,
    'S' -> 1,
    'T' -> 1,
    'U' -> 2,
    'V' -> 5,
    'W' -> 4,
    'X' -> 8,
    'Y' -> 3,
    'Z' -> 10
  )

  val bonuses: Map[(Int, Int), scrabble.Bonus] = Map(
    (0, 3) -> scrabble.Bonus.TW,
    (0, 6) -> scrabble.Bonus.TL,
    (1, 2) -> scrabble.Bonus.DL,
    (1, 5) -> scrabble.Bonus.DW,
    (2, 1) -> scrabble.Bonus.DL,
    (2, 4) -> scrabble.Bonus.DL,
    (3, 0) -> scrabble.Bonus.TW,
    (3, 3) -> scrabble.Bonus.TL,
    (4, 2) -> scrabble.Bonus.DL,
    (4, 6) -> scrabble.Bonus.DL,
    (5, 1) -> scrabble.Bonus.DW,
    (5, 5) -> scrabble.Bonus.TL,
    (6, 0) -> scrabble.Bonus.TL,
    (6, 4) -> scrabble.Bonus.DL,

    (0, 11) -> scrabble.Bonus.TW,
    (0, 8) -> scrabble.Bonus.TL,
    (1, 12) -> scrabble.Bonus.DL,
    (1, 9) -> scrabble.Bonus.DW,
    (2, 13) -> scrabble.Bonus.DL,
    (2, 10) -> scrabble.Bonus.DL,
    (3, 14) -> scrabble.Bonus.TW,
    (3, 11) -> scrabble.Bonus.TL,
    (4, 12) -> scrabble.Bonus.DL,
    (4, 8) -> scrabble.Bonus.DL,
    (5, 13) -> scrabble.Bonus.DW,
    (5, 9) -> scrabble.Bonus.TL,
    (6, 14) -> scrabble.Bonus.TL,
    (6, 10) -> scrabble.Bonus.DL,

    (14, 3) -> scrabble.Bonus.TW,
    (14, 6) -> scrabble.Bonus.TL,
    (13, 2) -> scrabble.Bonus.DL,
    (13, 5) -> scrabble.Bonus.DW,
    (12, 1) -> scrabble.Bonus.DL,
    (12, 4) -> scrabble.Bonus.DL,
    (11, 0) -> scrabble.Bonus.TW,
    (11, 3) -> scrabble.Bonus.TL,
    (10, 2) -> scrabble.Bonus.DL,
    (10, 6) -> scrabble.Bonus.DL,
    (9, 1) -> scrabble.Bonus.DW,
    (9, 5) -> scrabble.Bonus.TL,
    (8, 0) -> scrabble.Bonus.TL,
    (8, 4) -> scrabble.Bonus.DL,

    (14, 11) -> scrabble.Bonus.TW,
    (14, 8) -> scrabble.Bonus.TL,
    (13, 12) -> scrabble.Bonus.DL,
    (13, 9) -> scrabble.Bonus.DW,
    (12, 13) -> scrabble.Bonus.DL,
    (12, 10) -> scrabble.Bonus.DL,
    (11, 14) -> scrabble.Bonus.TW,
    (11, 11) -> scrabble.Bonus.TL,
    (10, 12) -> scrabble.Bonus.DL,
    (10, 8) -> scrabble.Bonus.DL,
    (9, 13) -> scrabble.Bonus.DW,
    (9, 9) -> scrabble.Bonus.TL,
    (8, 14) -> scrabble.Bonus.TL,
    (8, 10) -> scrabble.Bonus.DL,

    (3, 7) -> scrabble.Bonus.DW,
    (7, 3) -> scrabble.Bonus.DW,
    (7, 11) -> scrabble.Bonus.DW,
    (11, 7)-> scrabble.Bonus.DW

  )
  test("Main.feasible_simple") {
     val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
     assert(scrabble.Board.positionFeasible((6, 6), scrabble.Direction.Vertical, board, 7) === true)
   }
  test("Main.feasible_simple2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((0, 0), scrabble.Direction.Vertical, board, 7) === false)
  }
  test("Main.feasible_simple3") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((0, 0), scrabble.Direction.Horizontal, board, 7) === false)
  }
  test("Main.feasible_simple4") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((6, 4), scrabble.Direction.Horizontal, board, 7) === true)
  }
  test("Main.feasible_simple5") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((7, 4), scrabble.Direction.Horizontal, board, 7) === true)
  }
  test("Main.feasible_simple6") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((8, 4), scrabble.Direction.Horizontal, board, 7) === true)
  }
  test("Main.feasible_simple7") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((6, 4), scrabble.Direction.Vertical, board, 7) === false)
  }
  test("Main.feasible_simple8") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((6, 5), scrabble.Direction.Vertical, board, 7) === true)
  }
  test("Main.feasible_simple9") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((7, 7), scrabble.Direction.Horizontal, board, 7) === true)
  }
  test("Main.feasible_simple10") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((7, 7), scrabble.Direction.Vertical, board, 7) === true)
  }
  test("Main.feasible_simple11") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((6, 7), scrabble.Direction.Vertical, board, 1) === true)
  }
  test("Main.feasible_simple12") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((5, 7), scrabble.Direction.Vertical, board, 1) === false)
  }
  test("Main.feasible_simple13") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((6, 7), scrabble.Direction.Horizontal, board, 1) === true)
  }
  test("Main.feasible_simple14") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((5, 7), scrabble.Direction.Horizontal, board, 1) === false)
  }
  test("Main.feasible_simple15") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((0, 14), scrabble.Direction.Horizontal, board, 1) === false)
  }
  test("Main.feasible_simple16") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((0, 14), scrabble.Direction.Horizontal, board, 5) === false)
  }
  test("Main.feasible_simple17") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Board.positionFeasible((0, 14), scrabble.Direction.Vertical, board, 1) === false)
  }

  test("Main.emptytiles_simple1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Util.countEmptyTilesInSlice(board(7).slice(6, 6 + 2)) === 0)
  }

  test("Main.feasiblemoves_simple1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.existFeasibleMovesGivenLength(board, 2, (7, 6), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Horizontal) === false)
  }
  test("Main.feasiblemoves_simple2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.existFeasibleMovesGivenLength(board, 5, (7, 5), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Horizontal) === true)
  }
  test("Main.feasiblemoves_simple3") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.existFeasibleMovesGivenLength(board, 2, (7, 6), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Vertical) === true)
  }
  test("Main.feasiblemoves_simple4") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.existFeasibleMovesGivenLength(board, 5, (7, 5), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Vertical) === true)
  }
  test("Main.feasiblemoves_simple5") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.existFeasibleMovesGivenLength(board, 1, (7, 6), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Vertical) === false)
  }

  test("Main.regexstring_simple1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.generateMainRegexString(board, 5, (7, 5), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Horizontal).contains("(?m)^[AETZ]BAT[AETZ]$"))
  }
  test("Main.regexstring_simple2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.generateMainRegexString(board, 5, (7, 6), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Vertical).contains("(?m)^B[AETZ][AETZ][AETZ][AETZ]$"))
  }

  test("Main.regexwords_simple1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    val regex: Option[String] = scrabble.Move.generateMainRegexString(board, 5, (7, 5), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Horizontal)
    val wordlist: String = scrabble.Util.loadWordlist("data/wwfwordlist.txt")
    assert(scrabble.Move.getWordsFromRegexString(regex, wordlist) === (List("ABATE")))
  }

  test("Main.lettercounts_simple1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.checkLetterCounts(board, 5, (7,5), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Horizontal, "ABATE") === true)
  }
  test("Main.lettercounts_simple2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.checkLetterCounts(board, 5, (7,5), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Horizontal, "ABATA") === false)
  }
  test("Main.lettercounts_simple3") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.checkLetterCounts(board, 5, (7,6), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Vertical, "BATZE") === true)
  }
  test("Main.lettercounts_simple4") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.checkLetterCounts(board, 5, (7,7), List('A', 'E', 'T', 'Z', 'Z', 'Z', 'Z'), scrabble.Direction.Vertical, "AAAZE") === false)
  }


  test("Main.adjacentwordposition_simple1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.getAdjacentWordAtPosition(board, (7, 5), scrabble.Direction.Vertical, 'A').isEmpty)
  }
  test("Main.adjacentwordposition_adjacent1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/adjacent_1.txt")
    assert(scrabble.Move.getAdjacentWordAtPosition(board, (7, 5), scrabble.Direction.Vertical, 'A').getOrElse((0,0), scrabble.Direction.Horizontal, "")._3.contains("TESTA"))
  }
  test("Main.adjacentwordposition_adjacent2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/adjacent_2.txt")
    assert(scrabble.Move.getAdjacentWordAtPosition(board, (7, 5), scrabble.Direction.Vertical, 'A').getOrElse((0,0), scrabble.Direction.Horizontal, "")._3.contains("TESTATIME"))
  }
  test("Main.adjacentwordposition_simple2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.getAdjacentWordAtPosition(board, (7, 5), scrabble.Direction.Horizontal, 'A').getOrElse((0,0), scrabble.Direction.Horizontal, "")._3.contains("ABAT"))
  }
  test("Main.adjacentwordposition_adjacent3") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/adjacent_1.txt")
    assert(scrabble.Move.getAdjacentWordAtPosition(board, (2, 5), scrabble.Direction.Horizontal, 'A').isEmpty)
  }
  test("Main.adjacentwordposition_adjacent4") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/adjacent_2.txt")
    assert(scrabble.Move.getAdjacentWordAtPosition(board, (8, 4), scrabble.Direction.Horizontal, 'A').getOrElse((0,0), scrabble.Direction.Horizontal, "")._3.contains("AT"))
  }


  test("Main.adjacentwordlist_adjacent1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/adjacent_1.txt")
    assert(scrabble.Move.getFormedAdjacentWords(board, ((7, 5), scrabble.Direction.Horizontal, "ABATE")) ===  List(((3,5),scrabble.Direction.Vertical,"TESTA")))
  }
  test("Main.adjacentwordlist_adjacent2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/adjacent_1.txt")
    assert(scrabble.Move.getFormedAdjacentWords(board, ((4, 6), scrabble.Direction.Vertical, "TEAB")) === List(((4,5),scrabble.Direction.Horizontal,"ET"), ((5,5),scrabble.Direction.Horizontal,"SE"), ((6,5),scrabble.Direction.Horizontal,"TA")))
  }
  test("Main.adjacentwordlist_adjacent3") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/adjacent_1.txt")
    assert(scrabble.Move.getFormedAdjacentWords(board, ((8, 7), scrabble.Direction.Horizontal, "TEAB")) === List(((7,7),scrabble.Direction.Vertical,"AT"), ((7,8),scrabble.Direction.Vertical,"TE")))
  }


  test("Main.validwords_simple1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    val regex: Option[String] = scrabble.Move.generateMainRegexString(board, 6, (7, 5), List('A', 'E', 'T', 'L', 'D', 'S', 'R'), scrabble.Direction.Horizontal)
    val wordlist: String = scrabble.Util.loadWordlist("data/wwfwordlist.txt")
    assert(scrabble.Move.getWordsFromRegexString(regex, wordlist) === (List("ABATED", "ABATER", "ABATES")))
  }

  test("Main.simple_score1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.getletterScoreListPlayed(board, ((7,6), scrabble.Direction.Horizontal, "BAT"), letterScores, bonuses) === List(('B', 4, None), ('A', 1, None), ('T', 1, None)))
  }

  test("Main.simple_score2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.getletterScoreListBoard(board, ((7,6), scrabble.Direction.Horizontal,"BAT"), letterScores, bonuses, blanks) === List[(Char, Int, Option[scrabble.Bonus])]())
  }

  test("Main.simple_score3") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((7,6), scrabble.Direction.Horizontal, "BAT"), letterScores, bonuses, blanks) === 6)
  }

  test("Main.simple_score4") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Scoring.getletterScoreListPlayed(board, ((7,5), scrabble.Direction.Horizontal, "ABATE"), letterScores, bonuses) === List[(Char, Int, Option[scrabble.Bonus])](('A', 1, None), ('E', 1, None)))
  }

  test("Main.simple_score5") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Scoring.getletterScoreListBoard(board, ((7,5), scrabble.Direction.Horizontal, "ABATE"), letterScores, bonuses, blanks) === List[(Char, Int, Option[scrabble.Bonus])](('B', 4, None), ('A', 1, None), ('T', 1, None)))
  }

  test("Main.simple_score6") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Scoring.scoreWithAdjacents(board, ((7,5), scrabble.Direction.Horizontal, "ABATE"), letterScores, bonuses, 35, blanks) === 8)
  }

  test("Main.vertical_simple_score1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.getletterScoreListPlayed(board, ((6,7), scrabble.Direction.Vertical, "BAT"), letterScores, bonuses) === List(('B', 4, None), ('A', 1, None), ('T', 1, None)))
  }

  test("Main.vertical_simple_score2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.getletterScoreListBoard(board, ((6,7), scrabble.Direction.Vertical, "BAT"), letterScores, bonuses, blanks) === List[(Char, Int, Option[scrabble.Bonus])]())
  }

  test("Main.vertical_simple_score3") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((6,7), scrabble.Direction.Vertical, "BAT"), letterScores, bonuses, blanks) === 6)
  }

  test("Main.vertical_simple_score4") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Scoring.getletterScoreListPlayed(board, ((6,7), scrabble.Direction.Vertical, "BAT"), letterScores, bonuses) === List[(Char, Int, Option[scrabble.Bonus])](('B', 4, None), ('T', 1, None)))
  }

  test("Main.vertical_simple_score5") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Scoring.getletterScoreListBoard(board, ((6,7), scrabble.Direction.Vertical, "BAT"), letterScores, bonuses, blanks) === List[(Char, Int, Option[scrabble.Bonus])](('A', 1, None)))
  }

  test("Main.vertical_simple_score6") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((6,7), scrabble.Direction.Vertical, "BAT"), letterScores, bonuses, blanks) === 6)
  }

  test("Main.vertical_simple_score7") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Scoring.scoreWithAdjacents(board, ((7,5), scrabble.Direction.Horizontal, "ABATERS"), letterScores, bonuses, 35, blanks) === 20 )
  }

  test("Main.bonus_score_triple_letter") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((0,5), scrabble.Direction.Horizontal, "BAT"), letterScores, bonuses, blanks) === 8)
  }

  test("Main.bonus_score_double_letter_vertical") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((0,2), scrabble.Direction.Vertical, "BAT"), letterScores, bonuses, blanks) === 7)
  }

  test("Main.bonus_score_triple_word") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((0,1), scrabble.Direction.Horizontal, "BAT"), letterScores, bonuses, blanks) === 18)
  }

  test("Main.bonus_score_triple_word_and_letter_and_bonus") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.scoreWithAdjacents(board, ((0,2), scrabble.Direction.Horizontal, "TESTING"), letterScores, bonuses, 35, blanks) === 89 )
  }

  test("Main.bonus_score_triple_word_and_letter") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((0,2), scrabble.Direction.Horizontal, "TESTED"), letterScores, bonuses, blanks) === 27)
  }

  test("Main.bonus_score_two_double_word_vertical") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((5,1), scrabble.Direction.Vertical, "TESTED"), letterScores, bonuses, blanks) === 28)
  }

  test("Scoring.score_validwords_simple1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    val wordlist: String = scrabble.Util.loadWordlist("data/wwfwordlist.txt")
    val wordlistSet: Set[String] = scrabble.Util.getWordlistAsSet(wordlist)
    assert(scrabble.Scoring.scoreValidWords(board, (7,5), List('A', 'E', 'T', 'L', 'D', 'S', 'R'), scrabble.Direction.Horizontal, wordlist, wordlistSet, letterScores, bonuses, 35, blanks) ===
      List((((7,5),scrabble.Direction.Horizontal,"ABATE"),5,8), (((7,5),scrabble.Direction.Horizontal,"ABATED"),6,10), (((7,5),scrabble.Direction.Horizontal,"ABATER"),6,9), (((7,5),scrabble.Direction.Horizontal,"ABATES"),6,9), (((7,5),scrabble.Direction.Horizontal,"ABATERS"),7,20)))
  }

  test("Main.bestmoves_simple1") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    val wordlist: String = scrabble.Util.loadWordlist("data/wwfwordlist.txt")
    val wordlistSet: Set[String] = scrabble.Util.getWordlistAsSet(wordlist)
    assert(scrabble.Scoring.getBestMoves(board, List('A', 'E', 'T', 'L', 'D', 'S', 'R'), wordlist, wordlistSet, letterScores, bonuses, 35, 1, blanks)
      === List((((1,9),scrabble.Direction.Vertical,"DARTLES"),7,68)))
  }

  test("Main.bestmoves_simple2") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove.txt")
    val wordlist: String = scrabble.Util.loadWordlist("data/wwfwordlist.txt")
    val wordlistSet: Set[String] = scrabble.Util.getWordlistAsSet(wordlist)
    assert(scrabble.Scoring.getBestMoves(board, List('O', 'U', 'U', 'T', 'R', 'N', 'N'), wordlist, wordlistSet, letterScores, bonuses, 35, 1, blanks)
      === List((((4,10),scrabble.Direction.Vertical,"RUNOUT"),6,21)))
  }

  test("Main.bestmoves_simple3") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove_2.txt")
    val wordlist: String = scrabble.Util.loadWordlist("data/wwfwordlist.txt")
    val wordlistSet: Set[String] = scrabble.Util.getWordlistAsSet(wordlist)
    assert(scrabble.Scoring.getBestMoves(board, List('E', 'F', 'D', 'I', 'O', 'A', 'W'), wordlist, wordlistSet, letterScores, bonuses, 35, 1, blanks )
      === List((((6,4),scrabble.Direction.Horizontal,"WAIFED"),6,30)))
  }

  test("Main.bestmoves_simple4") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/example.txt")
    val wordlist: String = scrabble.Util.loadWordlist("data/wwfwordlist.txt")
    val wordlistSet: Set[String] = scrabble.Util.getWordlistAsSet(wordlist)
    assert(scrabble.Scoring.getBestMoves(board, List('E', 'O', 'W', 'U', 'T', 'A', 'T'), wordlist, wordlistSet, letterScores, bonuses, 35, 2, blanks )
      === List((((12,2),scrabble.Direction.Horizontal,"TOW"),3,20), (((3,7),scrabble.Direction.Horizontal,"WAFT"),4,20)))
  }

  test("Main.movefeasible1") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove.txt")
    assert(scrabble.Move.moveFeasible(((0,9),scrabble.Direction.Vertical,"OUTRUN"), board) === false)
  }
  test("Main.movefeasible2") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove.txt")
    assert(scrabble.Move.moveFeasible(((1,9),scrabble.Direction.Vertical,"OUTRUN"), board) === false)
  }
  test("Main.movefeasible3") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove.txt")
    assert(scrabble.Move.moveFeasible(((2,9),scrabble.Direction.Vertical,"OUTRUN"), board) === true)
  }
  test("Main.mainmovefeasible1") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove_2.txt")
    assert(scrabble.Move.checkMainMoveValidInSlice(((7,2),scrabble.Direction.Horizontal,"QADIS"), board) === false)
  }
  test("Main.mainmovefeasible2") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove_2.txt")
    assert(scrabble.Move.checkMainMoveValidInSlice(((7,2),scrabble.Direction.Horizontal,"QADIS"), board) === false)
  }
  test("Main.mainmovefeasible3") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove_2.txt")
    assert(scrabble.Move.checkMainMoveValidInSlice(((2,6),scrabble.Direction.Vertical,"BOASTS"), board) === true)
  }

  test("Main.mainmovefeasible4") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.checkMainMoveValidInSlice(((7,5),scrabble.Direction.Horizontal,"ABATE"), board) === true)
  }
  test("Main.mainmovefeasible5") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove.txt")
    assert(scrabble.Move.checkMainMoveValidInSlice(((4,10),scrabble.Direction.Vertical,"RUNOUT"), board) === true)
  }
  test("Main.mainmovefeasible6") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/simple.txt")
    assert(scrabble.Move.checkMainMoveValidInSlice(((7,5),scrabble.Direction.Horizontal,"ABA"), board) === false)
  }
  test("Main.mainmovefeasible7") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/latemove.txt")
    assert(scrabble.Move.checkMainMoveValidInSlice(((9,11),scrabble.Direction.Vertical,"MOJO"), board) === false)
  }

  test("Main.mainmovefeasible8") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/2ndmove_2.txt")
    assert(scrabble.Move.checkMainMoveValidInSlice(((7,10),scrabble.Direction.Horizontal,"BOASTS"), board) === false)
  }

  test("Main.lettercounts1") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/blanks.txt")
    assert(scrabble.Move.checkLetterCounts(board, 5, (7,6), List[Char]('R','O','T','O','*','Z','Z'), scrabble.Direction.Vertical, "BOTOX" ) === true)
  }

  test("Main.lettercounts2") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/blanks.txt")
    assert(scrabble.Move.checkLetterCounts(board, 7, (7,6), List[Char]('R','O','T','O','*','*','*'), scrabble.Direction.Vertical, "BOTOXIN" ) === true)
  }

  test("Main.lettercounts3") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/blanks.txt")
    assert(scrabble.Move.checkLetterCounts(board, 6, (7,6), List[Char]('R','O','T','O','*','Z','Z'), scrabble.Direction.Vertical, "BOTOXI" ) === false)
  }

  test("Main.simple_blanks") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/blanks.txt")
    assert(blanks === Set[(Int, Int)]((7,6),(7,7),(7,8)))
  }

  test("Main.simple_blanks_score1") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/blanks.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((7,6), scrabble.Direction.Horizontal, "BAT"), letterScores, bonuses, blanks) === 0)
  }

  test("Main.simple_blanks_score2") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/blanks.txt")
    assert(scrabble.Scoring.scoreWordBase(board, ((7,5), scrabble.Direction.Horizontal, "ABATE"), letterScores, bonuses, blanks) === 2)
  }

  test("Main.simple_blanks_score3") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/blanks.txt")
    assert(scrabble.Scoring.getletterScoreListPlayed(board, ((7,5), scrabble.Direction.Horizontal, "ABATE"), letterScores, bonuses) === List[(Char, Int, Option[scrabble.Bonus])](('A', 1, None), ('E', 1, None)))
  }

  test("Main.simple_blanks_score4") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/blanks.txt")
    assert(scrabble.Scoring.getletterScoreListBoard(board, ((7,5), scrabble.Direction.Horizontal, "ABATE"), letterScores, bonuses, blanks) === List[(Char, Int, Option[scrabble.Bonus])](('B', 0, None), ('A', 0, None), ('T', 0, None)))
  }
  test("Main.board_nonempty") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/blanks.txt")
    assert(scrabble.Board.isBoardEmpty(board) === false)
  }
  test("Main.board_empty") {
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    assert(scrabble.Board.isBoardEmpty(board) === true)
  }
  test("Main.firstmoves_simple1") {
    // Add score from other formed words
    val (board, blanks) = scrabble.Board.loadScrabble("src/test/data/empty.txt")
    val wordlist: String = scrabble.Util.loadWordlist("data/wwfwordlist.txt")
    val wordlistSet: Set[String] = scrabble.Util.getWordlistAsSet(wordlist)
    assert(scrabble.Scoring.getBestMoves(board, List('A', 'E', 'T', 'L', 'D', 'S', 'R'), wordlist, wordlistSet, letterScores, bonuses, 35, 1, blanks)
      === List((((7,7),scrabble.Direction.Horizontal,"DARTLES"),7,53)))
  }
}
