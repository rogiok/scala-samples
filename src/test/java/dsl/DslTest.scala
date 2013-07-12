package dsl

import org.specs2.mutable._
//import dsl.GeneralDsl._

/**
 * User: Rogerio
 * Date: 7/11/13
 * Time: 6:08 PM
 */
class DslTest extends Specification {

  "Autenticação do usuário" should {
    "aceitar o usuário e senha válido" in {

//      implicit def stringsToUser(name: String) = User(name, "")

      new User().login("user").passwd("abc")


//      User login "user"
//      User pass "abc"
//      User w ("sdf")



      new Abc().d("add")("sss")

//      implicit def stringsToUserLogin(name: String) = UserLogin(name)
//      implicit def stringsToUserPass(name: Int) = UserPass(name)

//      UserB login("user" pass 10)

//      (User authenticate) mustEqual true

      true mustEqual true
    }
  }

}

/*
abstract class Term
case class Func(a: String) extends Term
case class Integer(v: Int) extends Term

trait GeneralDsl {

  def login(user: String, pass: String) = {
    true
  }

  def login(user: String) = {
    true
  }

}
*/

//case class Func(a: String)

class User(var name: String = "", var pass: String = "") {
  def login(name: String) = {
    this.name = name

    this
  }

  def passwd(password: String) = {
    this.pass = password

    this
  }
}

class Abc {
  def d(p: String)(d: String) {

  }
}

/*
object UserA {

  var user = User()

  def login(name: String) {
    user.name = name
  }

  def pass(password: String) {
    user.pass = password
  }

  def w(u: User) {

  }

  def authenticate: Boolean = {

    println(user.name + " - " + user.pass)

    true
  }
}

case class UserPass(p: Int) {
}

case class UserLogin(n: String) {
  def pass(up: UserPass) = {
     this
  }
}

object UserB {
  def login(l: UserLogin) {

  }
}
*/