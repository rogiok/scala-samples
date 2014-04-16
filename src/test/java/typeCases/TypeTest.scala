package typeCases

import org.junit.Test

/**
 * Created by Rogerio on 1/9/14 4:17 PM.
 */
class TypeTest {

  @Test def methodTest() {

    new MethodParam[TypeParam]().show[OneLevelParam](new OneLevelParam("100"))
    new MethodParam[OneLevelParam]().show[OneLevelParam](new OneLevelParam("100"))
    new MethodParam[OneLevelParam]().show[TwoLevelParam](new TwoLevelParam("100"))
//    new MethodParam[TwoLevelParam]().show[OneLevelParam](new OneLevelParam("100"))

  }

  @Test def method2Test() {

//    new MethodParam[TypeParam]().show[OneLevelParam](new OneLevelParam("100"))
//    new MethodParam[OneLevelParam]().show[OneLevelParam](new OneLevelParam("100"))
//    new MethodParam[OneLevelParam]().show[TwoLevelParam](new TwoLevelParam("100"))
        new MethodParam2[TwoLevelParam]().show[OneLevelParam](new OneLevelParam("100"))

  }

}

class MethodParam[B] {

  def show[T <: B](input: T) {

    input match {
      case x: OneLevelParam =>
        println(s"One: ${x.x}")
      case x: TwoLevelParam =>
        println(s"Two: ${x.x}")
      case x: ThreeLevelParam =>
        println(s"Three: ${x.x}")
      case _ =>
        println("Invalid")
    }
  }

}

class MethodParam2[+B] {

  def show[T >: B](input: T) {

    input match {
      case x: OneLevelParam =>
        println(s"One: ${x.x}")
      case x: TwoLevelParam =>
        println(s"Two: ${x.x}")
      case x: ThreeLevelParam =>
        println(s"Three: ${x.x}")
      case _ =>
        println("Invalid")
    }
  }

}

class TypeParam(val x: String)
class OneLevelParam(y: String) extends TypeParam(y)
class TwoLevelParam(w: String) extends OneLevelParam(w)
class ThreeLevelParam(z: String) extends TwoLevelParam(z)
