name := "scala-sudoku-solver"

version := "0.1"
scalacOptions += "-Ypartial-unification"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0-M1"

scalaVersion := "2.12.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

wartremoverErrors ++= Warts.all
wartremoverWarnings ++= Warts.all    // or Warts.unsafe

addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.17")

val enumeratumVersion = "1.5.13"
libraryDependencies ++= Seq(
    "com.beachape" %% "enumeratum" % enumeratumVersion
)