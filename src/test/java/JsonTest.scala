import beans.BeanProperty
import javax.xml.bind.annotation.{XmlElement, XmlRootElement}
import org.codehaus.jackson.annotate.{JsonTypeName, JsonIgnoreProperties}
import org.codehaus.jackson.map.{AnnotationIntrospector, DeserializationConfig, SerializationConfig, ObjectMapper}
import org.junit.Test

/**
 * Date: 11/11/12 5:53 PM
 * @author Rogerio
 */
class JsonTest {

  @Test
  def read {

    val mapper: ObjectMapper = new ObjectMapper()

    mapper.getSerializationConfig.set(SerializationConfig.Feature.WRAP_ROOT_VALUE, true)
    mapper.getDeserializationConfig.set(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true)

    // val u: User = mapper.readValue("{\"user\": null}", classOf[User])
//    val u: User = mapper.readValue("{\"user\": { \"id\": \"10\" }}", classOf[User])
    val u: User = mapper.readValue("{\"User\": { \"id\": \"10\" } }", classOf[User])
//    val u: User = mapper.readValue("{ \"id\": \"10\" }", classOf[User])

    println(u.getId)

  }

  @Test
  def write {

    val mapper: ObjectMapper = new ObjectMapper()

    mapper.getSerializationConfig.set(SerializationConfig.Feature.WRAP_ROOT_VALUE, true)

    val u = new User

    u.id = "123"

    println(mapper.writeValueAsString(u))

  }
}

//@JsonTypeName("user")
@XmlRootElement(name="user")
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIgnoreProperties(Array{"user"})
class User {

  @BeanProperty var id: String = null

}
