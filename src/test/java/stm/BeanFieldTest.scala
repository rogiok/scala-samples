package stm

import scala.concurrent.stm._
import org.junit.Test
import org.junit.Assert._
import java.util.concurrent.{TimeUnit, Executors}
import scala.collection.mutable

/**
 * User: Rogerio
 * Date: 6/8/13
 * Time: 4:52 PM
 */
class BeanFieldTest {

  @Test def test {

    val beanService = new BeanService
    val executor = Executors.newScheduledThreadPool(10)

    for (i <- 0 until 20)
      executor.schedule(new Runnable() {
        override def run {
          beanService.update(s"bean${if(i % 2 == 0) 2 else 1}", new BeanField(1, 1))

          println(s"Updated bean${if(i % 2 == 0) 2 else 1}")
        }
      }, 1, TimeUnit.SECONDS)

//    for (i <- 0 until 20)
//      executor.schedule(new Runnable() {
//        override def run {
//          beanService.update(s"bean1", new BeanField(1, 1))
//
//          println(s"Updated bean1")
//        }
//      }, 1, TimeUnit.SECONDS)

    Thread.sleep(30000)

    executor.shutdown()

    println(s"final bean1 -> one: ${beanService.getBean("bean1").one} - two: ${beanService.getBean("bean1").two}")
    println(s"final bean2 -> one: ${beanService.getBean("bean2").one} - two: ${beanService.getBean("bean2").two}")

    assertEquals(10, beanService.getBean("bean1").one)
    assertEquals(10, beanService.getBean("bean1").two)
    assertEquals(10, beanService.getBean("bean2").one)
    assertEquals(10, beanService.getBean("bean2").two)

  }
}

class BeanService {

//  val map = new mutable.HashMap[String, Ref[BeanField]]
//  val bean = Ref[BeanField](new BeanField(0, 0))
  val map = TMap.empty[String, Ref[BeanField]]

//  init

//  def init {
//    val bean1 = Ref[BeanField](new BeanField(0, 0))

//    map += ("bean1" -> bean1)

//    val bean2 = Ref[BeanField](new BeanField(0, 0))

//    map += ("bean2" -> bean2)
//  }

  def update(id: String, pBean: BeanField) {
    atomic {
      implicit txn => {
//        println(s"Updating $id")

        val bean = map.getOrElseUpdate(id, Ref[BeanField](new BeanField(0, 0)))

        val nb = bean()

        println(s"Updating $id -> ${bean.toString} = ${nb.toString}")

        val newBean = new BeanField(nb.one + pBean.one, nb.two + pBean.two)

//        newBean.one += pBean.one
//        newBean.two += pBean.two

        println(s"${newBean.one} - ${newBean.two}")

        bean.swap(newBean)

        Thread.sleep(1000)
      }
    }
  }

  def getBean(id: String): BeanField = {
    val bean = map.single(id)

    bean.single.apply()
//    atomic {
//      implicit txn => {
//        bean()
//      }
//    }
  }

}

class BeanField(val one: Int, val two: Int) {
}
