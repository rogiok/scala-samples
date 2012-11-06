import java.io.{FileInputStream, InputStreamReader}
import org.junit.Test
import scala.math._
import xml.{Node, XML}

class XmlTest {

  @Test
  def readTest {
    val root: Node = XML.load(new InputStreamReader(this.getClass.getClassLoader.getResourceAsStream("copy-files.xml"), "UTF-8"))


    println((root \\ "property" \\ "@name").text)
    println((root \\ "taskdef" \\ "@name").text)

  }

}
