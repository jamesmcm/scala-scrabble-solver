// Copyright (C) 2019 James McMurray
//
// scala-scrabble-solver is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// scala-scrabble-solver is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with scala-scrabble-solver.  If not, see <http://www.gnu.org/licenses/>.

package scrabble
import scrabble.GameRules.getGameRules
import scrabble.Board._
import scrabble.Move._
import scrabble.Scoring._
import scrabble.Util._

object Main extends App {

  if (args.length < 2) {
    println("Usage: sbt run gametype BOARD.txt WORDLIST.txt LETTERS\ngametype: wwfmp, wwfsp, scrabble")
    System.exit(1)
  }


  val (board: Board, blanks: Set[Position]) = loadScrabble(args(1))
  val letters: Letters = args(3).toList
  val wordlist: String = loadWordlist(args(2))
  val setWordlist: Set[String] = getWordlistAsSet(wordlist)
  val (boardSize: Int, maxLetters: Int, letterScores: Map[Char, Int], bonuses: Map[Position, Bonus], bonusScore: Int) = getGameRules(args(0))

  println(getBestMoves(board, letters, wordlist, setWordlist, letterScores, bonuses, bonusScore, 20, blanks = blanks))



}
