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
import scrabble.Direction._
import scrabble.Util._

object Move {
  type Word = String
  type Letters = List[Char]
  type Move = (Position, Direction, Word)

  def moveFeasible(move: Move, board: Board): Boolean = {
    // Check if a given position is feasible given the number of tiles and direction of move:
    // Can we ever be adjacent to existing tiles from here?
    move._2 match {
      case Horizontal => board(move._1._1).slice(move._1._2, move._1._2 + move._3.length).exists(_.isDefined) ||
        (board.lift(move._1._1 + 1) match {
          case Some(x: Array[Option[Char]]) => x.slice(move._1._2, move._1._2 + move._3.length).exists(_.isDefined);
          case None => false
        }) ||
        (board.lift(move._1._1 - 1) match {
          case Some(x: Array[Option[Char]]) => x.slice(move._1._2, move._1._2 + move._3.length).exists(_.isDefined);
          case None => false
        })

      case Vertical => board.slice(move._1._1, move._1._1 + move._3.length).map(x => x(move._1._2)).exists(_.isDefined) ||
        board.slice(move._1._1, move._1._1 + move._3.length).map(x => x.lift(move._1._2 + 1)).map {
          case Some(y: Option[Char]) => y;
          case None => None
        }.exists(_.isDefined) ||
        board.slice(move._1._1, move._1._1 + move._3.length).map(x => x.lift(move._1._2 - 1)).map {
          case Some(y: Option[Char]) => y;
          case None => None
        }.exists(_.isDefined)
    }
  }

  def checkMainMoveValidInSlice(move: Move, board: Board): Boolean = {
    move._2 match {
      case Horizontal => board(move._1._1).lift(move._1._2 + move._3.length).flatten.isEmpty & board(move._1._1).lift(move._1._2 - 1).flatten.isEmpty
      case Vertical => ((x: Option[BoardSlice], y: Int) =>
        x match {
          case None => None
          case Some(z) => Some(z(y))
        }) (board.lift(move._1._1 + move._3.length), move._1._2).flatten.isEmpty & ((x: Option[BoardSlice], y: Int) =>
        x match {
          case None => None
          case Some(z) => Some(z(y))
        }) (board.lift(move._1._1 - 1), move._1._2).flatten.isEmpty
    }
  }


  def getValidWords(board: Board, len: Int, pos: Position, letters: Letters, direction: Direction, wordlist: String, wordlistSet: Set[String]): List[String] = {
    // Get tuple of (String, Boolean, Boolean) for letter count check, and adjacent words formed in word list check
    getWordsFromRegexString(generateMainRegexString(board, len, pos, letters, direction), wordlist)
      .map((x: String) => (x,
        checkLetterCounts(board, len, pos, letters, direction, x),
        checkAllWordsInWordlist(getFormedAdjacentWords(board, (pos, direction, x)).map(_._3), wordlistSet)
      ))
      .filter((x: (String, Boolean, Boolean)) => x._2 && x._3)
      .map((x: (String, Boolean, Boolean)) => x._1)
  }

  def getWordsFromRegexString(restr: Option[String], wordlist: String): List[String] = {
    restr match {
      case None => List()
      case Some(x: String) => x.r.findAllIn(wordlist).toList
    }
  }

  def checkLetterCounts(board: Board, len: Int, pos: Position, letters: Letters, direction: Direction, word: String): Boolean = {
    // Check that tile counts from the word match fit within the letter counts that we have
    // Do this check before getting adjacent words
    direction match {
      case Horizontal => checkTileCountsLEQLetters(getEmptyTilesRelativeIndexInSlice(board(pos._1).slice(pos._2, pos._2 + len))
        .map(x => word(x))
        .groupBy(identity).mapValues(_.size),
        letters.groupBy(identity).mapValues(_.size))
      case Vertical => checkTileCountsLEQLetters(getEmptyTilesRelativeIndexInSlice(
        board
          .map(x => x(pos._2))
          .slice(pos._1, pos._1 + len)
      )
        .map(x => word(x)).groupBy(identity).mapValues(_.size),
        letters.groupBy(identity).mapValues(_.size))
    }

  }

  def generateMainRegexString(board: Board, len: Int, pos: Position, letters: Letters, direction: Direction): Option[String] = {
    direction match {
      case Horizontal => if
      (existFeasibleMovesGivenLength(board, len, pos, letters, direction)) {
        Some("(?m)^" +
          board(pos._1).slice(pos._2, pos._2 + len).map {
            case None => if (letters.contains('*')) "[A-Z]" else "[" + letters.distinct.mkString + "]";
            case Some(x: Char) => x.toString
          }
            .mkString + "$")
      } else {
        None
      }
      case Vertical =>
        if (existFeasibleMovesGivenLength(board, len, pos, letters, direction)) {
          Some("(?m)^" +
            board.map(x => x(pos._2)).slice(pos._1, pos._1 + len).map {
              case None => if (letters.contains('*')) "[A-Z]" else "[" + letters.distinct.mkString + "]";
              case Some(x: Char) => x.toString
            }
              .mkString + "$")
        } else {
          None
        }
    }
  }

  def existFeasibleMovesGivenLength(board: Board, len: Int, pos: Position, letters: Letters, direction: Direction): Boolean = {
    // Check that moves at this position contain at least 1 empty tile
    // and do not require more empty tiles than we have letters
    direction match {
      case Horizontal => (countEmptyTilesInSlice(board(pos._1).slice(pos._2, pos._2 + len)) > 0) &&
        (countEmptyTilesInSlice(board(pos._1).slice(pos._2, pos._2 + len)) <= letters.length)
      case Vertical => (countEmptyTilesInSlice(
        board.map(x => x(pos._2))
          .slice(pos._1, pos._1 + len)) > 0) &&
        (countEmptyTilesInSlice(
          board.map(x => x(pos._2))
            .slice(pos._1, pos._1 + len)
        ) <= letters.length)
    }
  }

  def checkTileCountsLEQLetters(tilecounts: Map[Char, Int], letters: Map[Char, Int]): Boolean = {
    // Check that tile counts from the word match fit within the letter counts that we have - from Map
    tilecounts.map(ccnt => ccnt._2 - letters.getOrElse(ccnt._1, 0)).map((x: Int) => if (x > 0) x else 0).sum <= letters.getOrElse('*', 0)

  }

  def getFormedAdjacentWords(board: Board, move: Move): List[Move] = {
    // Map getAdjacentWordAtPosition over Positions of Empty Tiles, to get List of Adjacent Words
    // We can then check that all words in the list are in the wordlist set (or that the list is empty)

    // Only need to check for previously empty board tiles - i.e. placed tiles
    move._2 match {
      case Horizontal => getEmptyTilesRelativeIndexInSlice(board(move._1._1).slice(move._1._2, move._1._2 + move._3.length))
        .map(x => getAdjacentWordAtPosition(board, (move._1._1, x + move._1._2), Vertical, move._3(x)))
        .filter(_.isDefined)
        .map {
          case Some(x: Move) => x;
        }
      case Vertical => getEmptyTilesRelativeIndexInSlice(board.map(x => x(move._1._2)).slice(move._1._1, move._1._1 + move._3.length))
        .map(x => getAdjacentWordAtPosition(board, (x + move._1._1, move._1._2), Horizontal, move._3(x)))
        .filter(_.isDefined)
        .map {
          case Some(x: Move) => x;
        }
    }
  }

  def getAdjacentWordAtPosition(board: Board, position: Position,
                                checkDirection: Direction,
                                playedLetterAtPosition: Char): Option[Move] = {
    // Get largest slice with no Nones, that is total word formed
    ((x: Move) => if (x._3.length < 2) None else Some(x)) (
      ((x: (String, String, String)) =>
        checkDirection match {
          case Horizontal => ((position._1, position._2 - x._1.length), checkDirection, x._1 + x._2 + x._3)
          case Vertical => ((position._1 - x._1.length, position._2), checkDirection, x._1 + x._2 + x._3)
        }
        ) (
        getAdjacentWordTuplesAtPosition(board, position, checkDirection, playedLetterAtPosition)
      )
    )
  }

  def getAdjacentWordTuplesAtPosition(board: Board, position: Position, checkDirection: Direction, playedLetterAtPosition: Char): (String, String, String) = {
    checkDirection match {
      case Vertical => (board.slice(0, position._1).map(x => x.lift(position._2)).reverseMap {
        // Part Above
        case Some(y: Option[Char]) => y;
        case None => None
      }.takeWhile(_.isDefined).reverseMap {
        case Some(y: Char) => y;
        case None => '\0'
      }.mkString,
        playedLetterAtPosition.toString,
        board.slice(position._1 + 1, board.length).map(x => x.lift(position._2)).map {
          // Part Below
          case Some(y: Option[Char]) => y;
          case None => None
        }.takeWhile(_.isDefined).map {
          case Some(y: Char) => y;
          case None => '\0'
        }.mkString)
      case Horizontal => (board(position._1)
        .slice(0, position._2)
        .reverse
        .takeWhile(_.isDefined).reverseMap {
        case Some(y: Char) => y;
        case None => '\0'
      }.mkString,
        playedLetterAtPosition.toString,
        board(position._1).slice(position._2 + 1, board.length)
          .takeWhile(_.isDefined).map {
          case Some(y: Char) => y;
          case None => '\0'
        }.mkString)
    }

  }
}
