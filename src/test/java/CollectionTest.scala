package general

import org.junit.Test
import collection.mutable.Map
import collection.JavaConversions.propertiesAsScalaMap

class CollectionTest {

  @Test
  def conc {

    val props: Map[String, String] = propertiesAsScalaMap(System.getProperties)

    println(props("java.io.tmpdir"))

  }

}
