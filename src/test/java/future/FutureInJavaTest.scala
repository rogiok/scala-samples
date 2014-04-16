package future

import org.junit.Test
import java.util.concurrent._

/**
 * Created by Rogerio on 3/4/14 1:20 PM.
 */
class FutureInJavaTest {

  @Test def cancellingOne() {

    val executor: ExecutorService = Executors.newFixedThreadPool(2)


    val future: Future[String] = executor.submit[String](new MyCallable)

    Thread.sleep(1000)

    new Thread(new Runnable {
      override def run() {
        Thread.sleep(500)
        println(s"${Thread.currentThread().getName} Cancelling")
        future.cancel(false)
      }
    }).start()

    println(future.get(2, TimeUnit.SECONDS))

    println("End")

  }

}

class MyCallable extends Callable[String] {
  override def call(): String = {
    println(s"${Thread.currentThread().getName} Begin")

    Thread.sleep(500)

    println(s"${Thread.currentThread().getName} Continue")

    Thread.sleep(3000)

    "Hello"
  }
}