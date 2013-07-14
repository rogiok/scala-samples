package dsl

import org.scalatest.{FreeSpec, FunSuite}

/**
 * User: Rogerio
 * Date: 7/12/13
 * Time: 7:47 PM
 */
class CaseOneTest extends FunSuite with CaseOneFixture {

  test("Authenticate user") {

    assert(user login("", ""))

  }

}

trait CaseOneFixture {
  object user {
    def login(u: String, p: String): Boolean = {
      println("login")

      if (u == "")
        throw new Exception("Null values.")

      if (u == "a")
        false
      else
        true
    }
  }
}


class CaseTwoTest extends FunSuite with CaseOneFixture with CaseTwoFixture {

  test("Check content's user") {

    user login("j", "j")

    assert((userContent name) == "John")
    assert((userContent lastname) == "A")
  }
}

trait CaseTwoFixture {
  object userContent {
    def name: String = {
      println("name")

      "John"
    }

    def lastname: String = {
      println("lastname")

      "A"
    }
  }
}