package general

import org.scalatest.FunSuite

/**
 * Created by Rogerio on 2/18/15 9:35 AM.
 */
class CaseClassSuite extends FunSuite {

  test("Case one") {

    val e = Employee("abc", 0, 0)

    assert(e.name == "abc")

  }

  test("Case two") {

    val e = PersonB("abc", 0, 0)

    assert(e.name == "abc")

  }

}


abstract class PersonX {
  def name: String
  def age: Int
}

trait PersonY {
  var name: String
  var age: Int
}

case class Employee(var name: String = "", var age: Int = 0, var salary: Int = 0) extends PersonX

case class PersonB(var name: String, var age: Int, var salary: Int) extends PersonY



abstract class Term
case class Var(name: String) extends Term
case class Fun(arg: String, body: Term) extends Term
case class App(f: Term, v: Term) extends Term

