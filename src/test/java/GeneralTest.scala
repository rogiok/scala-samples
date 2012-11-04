import org.junit.Test
import scala.math._

/**
 * Created with IntelliJ IDEA.
 * User: Rogerio
 * Date: 10/21/12
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */
class GeneralTest {

  @Test
  def traitTest {

    var logger: Logger = new LoggerImpl()

    logger.log("abc")
    logger log "abc"

  }

  @Test
  def anotherFormToTraitTest {

    val s: ShowLog = new ShowLog with FileLogger

    s.show

  }

  @Test
  def applyAndUpdateTest {

    val x = Fraction(2, 3) * Fraction(3, 4)

    println(x)

  }

  @Test
  def unapply {
    val x = Fraction(2, 3)

    val Fraction(y) = x

    println(y)
  }

  @Test
  def function {

    val fun = ceil _

    println(fun(10.5))

    println(Array(1.2, 2.8).map(fun).array(0))

  }
}
