package existencial

import org.junit.Test
import org.junit.Assert._

/**
 * Created by Rogerio on 5/12/14 10:49 PM.
 */
class ExistencialTypeTest {

  @Test def testOne() {

    val x = new FooOne[String]()

    x.set("Hello")

    assertEquals("Hello", x.get)

  }

  @Test def testTwo() {

    val x = new FooTwo[String]()

    x.set("Hello")

    assertEquals("Hello", x.get)

  }

  @Test def testThree() {

    val x = new FooThree[String]()

    x.set("Hello")

    assertEquals("Hello", x.get)

  }

}

class FooOne[T] {

  private var a: T = _

  def get(): T = {
    a
  }

  def set(v: T) {
    a = v
  }
}

class FooTwo[T <: Z forSome { type Z }] {

  private var a: T = _

  def get(): T = {
    a
  }

  def set(v: T) {
    a = v
  }
}

class FooThree[_] {

  private var a: Any = _

  def get(): Any = {
    a
  }

  def set(v: Any) {
    a = v
  }
}
