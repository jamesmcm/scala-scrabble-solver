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

import cats.implicits._

import scrabble.Board._
import scrabble.Direction._
import scrabble.Bonus._
import scrabble.Util._
import scrabble.Move._

object Scoring {
  def getletterScoreListPlayed(board: Board, move: Move,
                               letterscores: Map[Char, Int],
                               bonuses: Map[Position, Bonus]): List[(Char, Int, Option[Bonus])] = {
    move._2 match {
      case Horizontal => getEmptyTilesRelativeIndexInSlice(board(move._1._1).slice(move._1._2, move._1._2 + move._3.length))
        .map((x: Int) => (move._3(x), letterscores(move._3(x)), bonuses.get((move._1._1, move._1._2 + x)))) // get (letter, letterScore, bonus) tuple
      case Vertical => getEmptyTilesRelativeIndexInSlice(board.map(x => x(move._1._2)).slice(move._1._1, move._1._1 + move._3.length))
        .map((x: Int) => (move._3(x), letterscores(move._3(x)), bonuses.get((move._1._1 + x, move._1._2)))) // get (letter, letterScore, bonus) tuple
    }
  }

  def getletterScoreListBoard(board: Board, move: Move,
                              letterscores: Map[Char, Int],
                              bonuses: Map[Position, Bonus], blanks: Set[Position]): List[(Char, Int, Option[Bonus])] = {
    move._2 match {
      case Horizontal => board(move._1._1).slice(move._1._2, move._1._2 + move._3.length).zip(List.range(0, move._3.length))
        .filter((x: (Option[Char], Int)) => x._1 match {
          case None => false
          case Some(_) => true
        })
        .map((x: (Option[Char], Int)) => x._1 match {
          case Some(y) => (y, x._2)
        })
        .map((x: (Char, Int)) => (x._1, if (blanks.contains(move._1._1, move._1._2 + x._2)) 0 else letterscores(x._1), bonuses.get((move._1._1, move._1._2 + x._2)))).toList // get (letter, letterScore, bonus) tuple

      case Vertical => board.map(x => x(move._1._2)).slice(move._1._1, move._1._1 + move._3.length).zip(List.range(0, move._3.length))
        .filter((x: (Option[Char], Int)) => x._1 match {
          case None => false
          case Some(_) => true
        })
        .map((x: (Option[Char], Int)) => x._1 match {
          case Some(y) => (y, x._2)
        })
        .map((x: (Char, Int)) => (x._1, if (blanks.contains(move._1._1 + x._2, move._1._2)) 0 else letterscores(x._1), bonuses.get((move._1._1 + x._2, move._1._2)))).toList // get (letter, letterScore, bonus) tuple
    }
  }

  def applyWordBonuses(playedScore: Tuple3[Int, Int, Int], boardScore: Int): Int = {
    (playedScore._1 + boardScore) * (scala.math.pow(2, playedScore._2).toInt) * (scala.math.pow(3, playedScore._3).toInt)
  }

  def scoreWordBase(board: Board, move: Move, letterscores: Map[Char, Int], bonuses: Map[Position, Bonus],
                    blanks: Set[Position]
                   ): Int = {
    // Get empty board positions and check bonuses, get letters from word for those
    // Map over non-empty board positions and add to total then apply word multipliers
    // Played tiles:
    applyWordBonuses(getletterScoreListPlayed(board, move, letterscores, bonuses).map(
      (x: (Char, Int, Option[Bonus])) => x._3 match {
        case Some(TL) => (x._2 * 3, 0, 0)
        case Some(DL) => (x._2 * 2, 0, 0)
        case Some(TW) => (x._2, 0, 1)
        case Some(DW) => (x._2, 1, 0)
        case _ => (x._2, 0, 0)
      }).foldLeft(0, 0, 0)((x: (Int, Int, Int), y: (Int, Int, Int)) => (x._1 + y._1, x._2 + y._2, x._3 + y._3)),
      getletterScoreListBoard(board, move, letterscores, bonuses, blanks).map( // Board tiles
        (x: (Char, Int, Option[Bonus])) => x._2).sum)
  }

  def scoreWithAdjacents(board: Board, move: Move, letterscores: Map[Char, Int],
                         bonuses: Map[Position, Bonus], bonusScore: Int, blanks: Set[Position]): Int = {
    (getFormedAdjacentWords(board, move) :+ move).map(scoreWordBase(board, _, letterscores, bonuses, blanks)).sum +
      ((x: List[(Char, Int, Option[Bonus])]) => x.length match {
        case 7 => bonusScore
        case _ => 0
      }
        ) (getletterScoreListPlayed(board, move, letterscores, bonuses))
  }

  def getBestMoves(board: Board, letters: Letters, wordlist: String,
                   wordlistSet: Set[String], letterscores: Map[Char, Int], bonuses: Map[Position, Bonus],
                   bonusScore: Int, limit: Int, blanks: Set[Position]): List[(Move, Int, Int)] = {
    if (isBoardEmpty(board)) List.range(2, 8).flatMap(getValidWords(board, _, (7, 7), letters, Direction.Horizontal, wordlist, wordlistSet))
      .map((x: String) => (((7, 7), Direction.Horizontal, x), x.length, scoreWithAdjacents(board, ((7, 7), Direction.Horizontal, x),
        letterscores, bonuses, bonusScore, blanks))).sortWith(_._3 > _._3).take(limit)
    else
      List.range(0, board.length)
        .foldLeft(List[(Int, Int)]())((y: List[(Int, Int)], x: Int) =>
          y ++ List.range(0, board.length).zipAll(List(), x, x))
        .foldLeft(List[(Position, Direction)]())((y: List[(Position, Direction)], x: Position) =>
          y ++ List[(Position, Direction)]((x, Horizontal), (x, Vertical)))
        .filter((x: (Position, Direction)) => positionFeasible(x._1, x._2, board, letters.length))
        .flatMap((x: (Position, Direction)) => scoreValidWords(board, x._1, letters, x._2, wordlist,
          wordlistSet, letterscores, bonuses, bonusScore, blanks)).sortWith(_._3 > _._3).take(limit)
  }

  def scoreValidWords(board: Board, pos: Position, letters: Letters, direction: Direction, wordlist: String,
                      wordlistSet: Set[String], letterscores: Map[Char, Int], bonuses: Map[Position, Bonus],
                      bonusScore: Int, blanks: Set[Position]): List[(Move, Int, Int)] = {
    List.range(2, board(0).length + 1).flatMap((l: Int) =>
      getValidWords(board, l, pos, letters, direction, wordlist, wordlistSet)
        .map((x: String) => ((pos, direction, x), l, scoreWithAdjacents(board, (pos, direction, x),
          letterscores, bonuses, bonusScore, blanks)))
        .filter((x: (Move, Int, Int)) => moveFeasible(x._1, board))
        .filter((x: (Move, Int, Int)) => checkMainMoveValidInSlice(x._1, board))
        .filter((x: (Move, Int, Int)) => x._1._3.length === x._2)
    )
  }
}

