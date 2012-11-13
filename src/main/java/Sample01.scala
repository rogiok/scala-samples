package general

import collection.mutable._

trait Logger {

  def log(msg: String) {
    println("C:" + msg)
  }
}

trait FileLogger extends Logger {

  override def log(msg: String) {
    println("F:" + msg)
  }
}

class LoggerImpl extends Logger {

  override def log(msg: String) {
    println(msg + ";")
  }
}

class ShowLog extends Logger {

  def show {
    log("Hello")
  }
}


class Fraction(val n: Int, val m: Int) {
  def *(a: Fraction) = n * m * a.n * a.m
}

object Fraction {
  def apply(n: Int, m: Int) = new Fraction(n, m)
  def unapply(f: Fraction) = {
    if (f.m == 0)
      None
    else
      Some(0)
  }
}


case class Another(value: String*) {

  def aa(p: String*) = "p=" + p.mkString

}

object Another


class Pair[T, V](val s: T, val x: V)

class AnotherPair[T <: Comparable[T]](val f: T, val s: T) {
  def smaller = if (f.compareTo(s) < 0) f else s
}

/*
Class has a view bound (<%) that converts using a implicit conversion (Int to RichInt). RichInt has the Comparable trait.
 */
class AnotherPair2[T <% Comparable[T]](val f: T, val s: T) {
  def smaller = if (f.compareTo(s) < 0) f else s
}

class Every {
  var id: String = null
  type Index = String HashMap Any // Infix for HashMap[String, Any]

  def setId(id: String): this.type = {
    this.id = id

    this
  }

  def abc {
    val x: Index = new Index[String, Any]

    x += ("abc" -> "123")
  }
}

class Person extends Every {
  var name: String = null

  def setName(n: String) = {
    this.name = n

    this
  }
}

class Student extends Person

class UpperBoundPair[T <: Person] // Accept Person and your subclasses
class LowerBoundPair[T >: Person] // Accept Person and your superclasses
//class ViewBoundPair[T <% Person]


