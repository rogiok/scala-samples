import general._
import org.junit.Test
import org.junit.Assert._

class TypeParameterTest {

  @Test
  def classType() {

    val p = new Pair[String, Int]("a", 1)

    println(p.s)

    assertEquals("a", p.s)

  }

  @Test
  def functionType {

    def getMiddle[T](a: Array[T]) = a(a.length / 2)

    val x = getMiddle[String] _

    val r = x(Array("abc", "def", "ghi"))
    val r2 = getMiddle[String](Array("123", "456", "789"))

    println(r)
    println(r2)

    assertEquals("def", r)
  }

  @Test
  def upperBound {

    val ap = new AnotherPair[String]("def", "abc")
    // val ap2 = new AnotherPair[Int](1, 0) // Invalid class for type parameter

    val r = ap.smaller

    println(r)

    assertEquals("abc", r)

  }

  @Test
  def viewBound {
    val ap = new AnotherPair2[Int](2, 0)

    val r = ap.smaller

    assertEquals(0, r)
  }

  @Test
  def upperBound2 {

    // Person to Student
    // UpperBoundPair[T <: Person]
    // val a = new UpperBoundPair[Every] // Invalid class
    val b = new UpperBoundPair[Person] // Accept only classes with Person and your subclasses
    val c = new UpperBoundPair[Student]

    assertTrue(true)

  }

  @Test
  def lowerBound {

    // LowerBoundPair[T >: Person]
    // val a = new LowerBoundPair[Student] // Invalid class
    val b = new LowerBoundPair[Every] // Accept Person and your superclasses
    val c = new LowerBoundPair[Person]

    assertTrue(true)

  }

}
