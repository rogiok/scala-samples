package general

import org.scalatest.FunSuite

class PatternMatchingSuite extends FunSuite {

  test("Pattern Matching 01") {

    val x = 10

    x match {
      case r if (r > 0) =>
        println("1")
      case r if (r < 0) =>
        println("2")
      case _ =>
        println("3")
    }
  }

  test("Pattern Matching 02") {

    val x = false
    val y = true

    (x, y) match {
      case (true, true) =>
        println("1")
      case (true, false) =>
        println("2")
      case (false, true) =>
        println("3")
      case (false, false) =>
        println("4")
    }

    if (x && y) {
      println("1")
    } else if (x && !y) {
      println("2")
    } else if (!x && y) {
      println("3")
    } else if (!x && !y) {
      println("4")
    }

    if (x) {
      if (y) {
        println("1")
      } else {
        println("2")
      }
    } else {
      if (y) {
        println("3")
      } else {
        println("4")
      }
    }

  }

  test("Pattern Matching 03") {

    val x = false
    val y = true
    val z = false

    (x, y, z) match {
      case (true, true, true) =>
        println("1")
      case (true, true, false) =>
        println("2")
      case (true, false, true) =>
        println("3")
      case (true, false, false) =>
        println("4")
      case (false, true, true) =>
        println("5")
      case (false, true, false) =>
        println("6")
      case (false, false, true) =>
        println("7")
      case (false, false, false) =>
        println("8")
    }

    if (x && y && z) {
      println("1")
    } else if (x && y && !z) {
      println("2")
    } else if (x && !y && z) {
      println("3")
    } else if (x && !y && !z) {
      println("4")
    } else if (!x && y && z) {
      println("5")
    } else if (!x && y && !z) {
      println("6")
    } else if (!x && !y && z) {
      println("7")
    } else if (!x && !y && !z) {
      println("8")
    }

    if (x) {
      if (y) {
        if (z) {
          println("1")
        } else {
          println("2")
        }
      } else {
        if (z) {
          println("3")
        } else {
          println("4")
        }
      }
    } else {
      if (y) {
        if (z) {
          println("5")
        } else {
          println("6")
        }
      } else {
        if (z) {
          println("7")
        } else {
          println("8")
        }
      }
    }

  }

  test("Pattern Matching 04") {

    trait Animal
    class Dog extends Animal
    class Cat extends Animal

    val x = new Dog

    x match {
      case r: Dog =>
        println("1")
      case r: Cat =>
        println("2")
      case r: Animal =>
        println("3")
    }

    if (x.isInstanceOf[Dog]) {
      println("1")
    } else if (x.isInstanceOf[Cat]) {
      println("2")
    } else if (x.isInstanceOf[Animal]) {
      println("3")
    }

  }

  test("Pattern Matching 05") {

    case class Player(var name: String, var age: Int)
    case class Player2(var name: String, var age: Int)

    val p = new Player("Joao", 10)

    p match {
      case Player(_, age) if age < 10 =>
        println("1")
      case Player(name, _) =>
        println(s"2 ${name}")
      case _ =>
        println("3")
    }

  }

}
