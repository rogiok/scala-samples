package general

import org.scalatest.FunSuite

/**
 * Created by Rogerio on 3/10/15 11:11 PM.
 */
class FunctionSuite extends FunSuite {

  test("Simple function 01") {

    val f = new Func01()

    f.exec("abc")(f.show)

  }

  def s(v: String): Unit = {

  }
}

class Func01 {

  def exec(value: String)(f: (String) => Unit) = {

    f(value)

  }

  def show(v: String): Unit = {

    println(v)

  }

}
