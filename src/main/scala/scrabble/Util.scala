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

import scrabble.Board._

object Util {

  def loadWordlist(filename: String): String = {
    // uppercase wordlist
    val source = scala.io.Source.fromFile(filename)
    try source.getLines.mkString("\n") finally source.close()
  }

  def getWordlistAsSet(wordlist: String): Set[String] = {
    wordlist.split('\n').toSet
  }

  def countEmptyTilesUntilFirstChar(slice: BoardSlice): Int = {
    slice.takeWhile((x: Option[Char]) => x.isEmpty).length
  }

  def countEmptyTilesInSlice(slice: BoardSlice): Int = {
    slice.count(_.isEmpty)
  }

  def getEmptyTilesRelativeIndexInSlice(slice: BoardSlice): List[Int] = {
    slice.zipWithIndex.collect { case (a, b) if a.isEmpty => b }.toList
  }

  def checkAllWordsInWordlist(words: List[String], setWordlist: Set[String]): Boolean = {
    words.map(x => checkWordInWordlist(x, setWordlist)).forall(identity)
  }

  def checkWordInWordlist(word: String, setWordlist: Set[String]): Boolean = {
    setWordlist.contains(word)
  }
}

