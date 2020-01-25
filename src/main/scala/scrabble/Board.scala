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

import enumeratum._
import cats.implicits._

sealed trait Bonus extends EnumEntry with Product with Serializable

object Bonus extends Enum[Bonus] {
  val values = findValues

  case object DL extends Bonus

  case object TL extends Bonus

  case object DW extends Bonus

  case object TW extends Bonus

}

sealed trait Direction extends EnumEntry with Product with Serializable

object Direction extends Enum[Direction] {
  val values = findValues

  case object Horizontal extends Direction

  case object Vertical extends Direction

}


object Board {

  import Direction._

  type BoardSlice = Array[Option[Char]] // Row or column
  type Board = Array[BoardSlice]
  type Position = (Int, Int)

  def isBoardEmpty(board: Board): Boolean = {
    board.forall(_.forall(_.isEmpty))
  }

  def parseBlankTiles(board: Board): (Board, Set[Position]) = {
    (board.map((x: Array[Option[Char]]) => x.filter(!_.contains('*'))),
      board.zipWithIndex.flatMap((x: (Array[Option[Char]], Int)) => x._1.zipWithIndex.filter(_._1.contains('*')).zipWithIndex.map((y: ((Option[Char], Int), Int)) => (x._2, y._1._2 - (1 + y._2)))).toSet)
  }

  def loadScrabble(filename: String): (Board, Set[Position]) = {
    // 15 x 15 board
    val form = (x: String) => if (x === "-") None else Some(x.charAt(0))
    val source = scala.io.Source.fromFile(filename)
    try parseBlankTiles(source.getLines.map((x: String) => x.split("").map(form)).toArray) finally source.close()
  }

  def positionFeasible(position: Position, direction: Direction, board: Board, numLetters: Int): Boolean = {
    // Return whether feasible
    // Check if a given position is feasible given the number of tiles and direction of move:
    // Can we ever be adjacent to existing tiles from here?
    direction match {
      case Horizontal => board(position._1).slice(position._2 + 1, position._2 + numLetters).exists(_.isDefined) ||
        (board.lift(position._1 + 1) match {
          case Some(x: Array[Option[Char]]) => x.slice(position._2, position._2 + numLetters).exists(_.isDefined);
          case None => false
        }) ||
        (board.lift(position._1 - 1) match {
          case Some(x: Array[Option[Char]]) => x.slice(position._2, position._2 + numLetters).exists(_.isDefined);
          case None => false
        })

      case Vertical => board.slice(position._1, position._1 + numLetters + 1).map(x => x(position._2)).exists(_.isDefined) ||
        board.slice(position._1, position._1 + numLetters).map(x => x.lift(position._2 + 1)).map {
          case Some(y: Option[Char]) => y;
          case None => None
        }.exists(_.isDefined) ||
        board.slice(position._1, position._1 + numLetters + 1).map(x => x.lift(position._2 - 1)).map {
          case Some(y: Option[Char]) => y;
          case None => None
        }.exists(_.isDefined)
    }
  }
}
