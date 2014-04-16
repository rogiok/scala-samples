package algorithm

import org.junit.Test
import com.google.common.base.Stopwatch

/**
 * User: Rogerio
 * Date: 12/17/13
 * Time: 10:12 AM
 */
class Fibonacci {

  def fibo(n: Long): Long =
    if (n < 2) 1
    else fibo(n - 1) + fibo(n - 2)

  def fiboD(n: Int): Long = {
    val numbers = new Array[Long](n + 1)

    numbers(0) = 1
    numbers(1) = 1

    for (i <- 2 to n) {
      numbers(i) = numbers(i - 2) + numbers(i - 1)
    }

    numbers(n)
  }

  @Test def test() {

    val s = new Stopwatch().start()
    println(fibo(50))
    s.stop()

    println(s)

    s.reset()
    s.start()
    println(fiboD(50))
    s.stop()

    println(s)

  }

}
