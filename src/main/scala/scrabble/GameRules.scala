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

import scrabble.Board.Position
import scrabble.Bonus._

object GameRules {
  val letterScores_wwf: Map[Char, Int] = Map(
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

  val letterScores_scrabble: Map[Char, Int] = Map(
    'A' -> 1,
    'B' -> 3,
    '*' -> 0,
    'C' -> 4,
    'D' -> 2,
    'E' -> 1,
    'F' -> 4,
    'G' -> 2,
    'H' -> 4,
    'I' -> 1,
    'J' -> 8,
    'K' -> 5,
    'L' -> 1,
    'M' -> 3,
    'N' -> 1,
    'O' -> 1,
    'P' -> 3,
    'Q' -> 10,
    'R' -> 1,
    'S' -> 1,
    'T' -> 1,
    'U' -> 1,
    'V' -> 4,
    'W' -> 4,
    'X' -> 8,
    'Y' -> 4,
    'Z' -> 10
  )

  val bonuses_wwfmp: Map[Position, Bonus] = Map(
    (0, 3) -> TW,
    (0, 6) -> TL,
    (1, 2) -> DL,
    (1, 5) -> DW,
    (2, 1) -> DL,
    (2, 4) -> DL,
    (3, 0) -> TW,
    (3, 3) -> TL,
    (4, 2) -> DL,
    (4, 6) -> DL,
    (5, 1) -> DW,
    (5, 5) -> TL,
    (6, 0) -> TL,
    (6, 4) -> DL,

    (0, 11) -> TW,
    (0, 8) -> TL,
    (1, 12) -> DL,
    (1, 9) -> DW,
    (2, 13) -> DL,
    (2, 10) -> DL,
    (3, 14) -> TW,
    (3, 11) -> TL,
    (4, 12) -> DL,
    (4, 8) -> DL,
    (5, 13) -> DW,
    (5, 9) -> TL,
    (6, 14) -> TL,
    (6, 10) -> DL,

    (14, 3) -> TW,
    (14, 6) -> TL,
    (13, 2) -> DL,
    (13, 5) -> DW,
    (12, 1) -> DL,
    (12, 4) -> DL,
    (11, 0) -> TW,
    (11, 3) -> TL,
    (10, 2) -> DL,
    (10, 6) -> DL,
    (9, 1) -> DW,
    (9, 5) -> TL,
    (8, 0) -> TL,
    (8, 4) -> DL,

    (14, 11) -> TW,
    (14, 8) -> TL,
    (13, 12) -> DL,
    (13, 9) -> DW,
    (12, 13) -> DL,
    (12, 10) -> DL,
    (11, 14) -> TW,
    (11, 11) -> TL,
    (10, 12) -> DL,
    (10, 8) -> DL,
    (9, 13) -> DW,
    (9, 9) -> TL,
    (8, 14) -> TL,
    (8, 10) -> DL,

    (3, 7) -> DW,
    (7, 3) -> DW,
    (7, 11) -> DW,
    (11, 7) -> DW

  )

  val bonuses_wwfsp: Map[Position, Bonus] = Map(
    (0, 2) -> TW,
    (0, 0) -> TL,
    (2, 2) -> DL,
    (1, 1) -> DW,
    (2, 4) -> DL,
    (2, 0) -> TW,
    (3, 3) -> TL,
    (4, 2) -> DL,

    (10, 2) -> TW,
    (10, 0) -> TL,
    (8, 2) -> DL,
    (9, 1) -> DW,
    (8, 4) -> DL,
    (8, 0) -> TW,
    (7, 3) -> TL,
    (6, 2) -> DL,

    (0, 8) -> TW,
    (0, 10) -> TL,
    (2, 8) -> DL,
    (1, 9) -> DW,
    (2, 6) -> DL,
    (2, 10) -> TW,
    (3, 7) -> TL,
    (4, 8) -> DL,

    (10, 8) -> TW,
    (10, 10) -> TL,
    (8, 8) -> DL,
    (9, 9) -> DW,
    (8, 6) -> DL,
    (8, 10) -> TW,
    (7, 7) -> TL,
    (6, 8) -> DL,

    (5, 1) -> DW,
    (1, 5) -> DW,
    (5, 9) -> DW,
    (9, 5) -> DW,
  )

  val bonuses_scrabble: Map[Position, Bonus] = Map(
    (0, 0) -> TW,
    (0, 3) -> DL,
    (1, 5) -> TL,
    (2, 6) -> DL,
    (3, 0) -> DL,
    (5, 1) -> TL,
    (6, 2) -> DL,
    (1, 1) -> DW,
    (2, 2) -> DW,
    (3, 3) -> DW,
    (4, 4) -> DW,
    (5, 5) -> TL,
    (6, 6) -> DL,

    (14, 0) -> TW,
    (14, 3) -> DL,
    (13, 5) -> TL,
    (12, 6) -> DL,
    (11, 0) -> DL,
    (9, 1) -> TL,
    (8, 2) -> DL,
    (13, 1) -> DW,
    (12, 2) -> DW,
    (11, 3) -> DW,
    (10, 4) -> DW,
    (9, 5) -> TL,
    (8, 6) -> DL,

    (0, 14) -> TW,
    (0, 11) -> DL,
    (1, 9) -> TL,
    (2, 8) -> DL,
    (3, 14) -> DL,
    (5, 13) -> TL,
    (6, 12) -> DL,
    (1, 13) -> DW,
    (2, 12) -> DW,
    (3, 11) -> DW,
    (4, 10) -> DW,
    (5, 9) -> TL,
    (6, 8) -> DL,

    (14, 14) -> TW,
    (14, 11) -> DL,
    (13, 9) -> TL,
    (12, 8) -> DL,
    (11, 14) -> DL,
    (9, 13) -> TL,
    (8, 12) -> DL,
    (13, 13) -> DW,
    (12, 12) -> DW,
    (11, 11) -> DW,
    (10, 10) -> DW,
    (9, 9) -> TL,
    (8, 8) -> DL,

    (0, 7) -> TW,
    (3, 7) -> DL,
    (14, 7) -> TW,
    (11, 7) -> DL,
    (7, 0) -> TW,
    (7, 3) -> DL,
    (7, 14) -> TW,
    (7, 11) -> DL,
  )

  def getGameRules(gamestring: String): (Int, Int, Map[Char, Int], Map[Position, Bonus], Int) = {
    gamestring match {
      case "wwfmp" => (15, 7, letterScores_wwf, bonuses_wwfmp, 35)
      case "wwfsp" => (11, 7, letterScores_wwf, bonuses_wwfsp, 35)
      case "scrabble" => (15, 7, letterScores_scrabble, bonuses_scrabble, 50)
    }
  }
}
