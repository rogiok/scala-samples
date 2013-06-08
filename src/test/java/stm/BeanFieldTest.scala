package stm

import scala.concurrent.stm._
import org.junit.Test
import java.util.concurrent.{TimeUnit, Executors}

/**
 * User: Rogerio
 * Date: 6/8/13
 * Time: 4:52 PM
 */
class BeanFieldTest {

  @Test def test {

    val beanService = new BeanService
    val executor = Executors.newScheduledThreadPool(10)

    beanService.init

    for (i <- 0 until 10)
      executor.schedule(new Runnable() {
        override def run {
          beanService.update(new BeanField(1, 1))

          println("Updated")
        }
      }, 1, TimeUnit.SECONDS)

    Thread.sleep(20000)

    executor.shutdown()

    println(s"final one: ${beanService.getBean.one} - two: ${beanService.getBean.two}")

  }
}

class BeanService {

  val bean = Ref.make[BeanField]()

  def init {
    atomic {
      implicit txn => {
        if (bean() == null)
          bean.swap(new BeanField(0, 0))
      }
    }
  }

  def update(pBean: BeanField) {
    atomic {
      implicit txn => {
        println("Updating...")

        val newBean = new BeanField(bean().one, bean().two)

        newBean.one += pBean.one
        newBean.two += pBean.two

        bean.swap(newBean)

        Thread.sleep(1000)
      }
    }
  }

  def getBean: BeanField = {
    atomic {
      implicit txn => {
        bean()
      }
    }
  }

}

class BeanField(var one: Int, var two: Int) {
//  var one = 0
//  var two = 0
}
