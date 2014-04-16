package implicitCases

import org.junit.Test

/**
 * Created by Rogerio on 1/8/14 11:23 AM.
 */
class ImplicitTest {

  val c = 10

  @Test def implicitParamTest() {

    implicit val ic = new ImplicitClass(100)
    implicit val f = (y: Int) => println(y)

    show(10)

    show2(10)

    show3 {
      a => println(a * a)
    }

  }

  def show(v: Int)(implicit ic: ImplicitClass) {
    println(v)
    println(ic.x)
  }

  def show2(v: Int)(implicit f: Int => Unit) {
    println(v)
    f(v)
  }

  def show3(f: Int => Unit) {
    f(c)
  }

}

class ImplicitClass(var x: Int)
