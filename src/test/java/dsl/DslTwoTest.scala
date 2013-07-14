package dsl

import org.scalatest._
import org.scalatest.selenium._
import org.openqa.selenium.{WebElement, WebDriver}
import org.scalatest.matchers.ShouldMatchers
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.junit.{JUnitSuite, JUnitRunner}
import org.junit.runner.RunWith
import org.scalatest.time.{Span, Seconds}
import org.scalatest.concurrent.Eventually
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.DesiredCapabilities

/**
 * User: Rogerio
 * Date: 7/11/13
 * Time: 9:45 PM
 */
//@RunWith(classOf[JUnitRunner])
class DslTwoTest extends GeneralFunctions with GivenWhenThen with ShouldMatchers {

//  val capabilities = DesiredCapabilities.chrome()

//  capabilities.setCapability("webdriver.chrome.driver", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome")
//  System.setProperty("webdriver.chrome.driver", "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome")
//  implicit val webDriver: WebDriver = new ChromeDriver(capabilities)

//  before {
//    println("OK")
//  }

//  after {
//    println("After outer")
    // Time to browser to finish its page loading
//    Thread.sleep(2000)

//    webDriver.close()
//    close()
//    quit()
//  }

//  feature("Autenticacao do usuario") {
//    scenario("usuario valido") {

//  behavior of "Autenticacao do usuario"
//  it should "ser valida" in {

  test("Autenticacao do usuario válida") {
//    Given("a URL")

    go to "http://bolao.esporte.uol.com.br"

    eventually {
//      currentUrl contains "competitions.do"
      find("user") should not be (None)
//      assert(textField("user").isDisplayed)
    }

    user name "rogiok"
    user password ""

    assert(user authenticate)

    user logout

//    When("put a text")
//    click on ("q")
//    textField("user").value = "rogiok"

//    webDriver.findElementById("pass").sendKeys("phantom4")

//    find("pass") match {
//      case Some(element) =>
//        element.
//        element.asInstanceOf[WebElement].sendKeys("phantom4")
//        element.attribute("value") match {
//          case Some(attrib) =>
//            attrib
//          case None =>
//        }
//      case None =>
//    }
//    click on ("pass")
//    textField("pass").value = "phantom4"

//    click on "enter"
//    submit()

//    Then("the result must be")

//    implicitlyWait(Span(5, Seconds))
//    wait(Span(5, Seconds))

//    eventually(timeout(Span(5, Seconds))) {
//      assert(currentUrl contains "competitions.html")
//      assert(pageTitle contains "Testing")
//    }

//  }

//  it should "be invalid" in {

//    println("OK")

  }

  test("Usuário deslogando") {

    go to "http://bolao.esporte.uol.com.br"

    eventually {
      find("user") should not be (None)
    }

    user name "rogiok"
    user password ""
    user authenticate

    assert(user logout)

  }

  test("Autenticacao invalida") {
    go to ("http://www.uol.com.br")

    eventually(timeout(Span(5, Seconds))) {
      assert(pageTitle contains "UOL")
    }
  }
//    scenario("asdfsfds") {

//    }
//  }

}

trait GeneralFunctions extends FunSuite with Firefox with Eventually with BeforeAndAfterAll with BeforeAndAfter {

//  implicit val webDriver: WebDriver = new FirefoxDriver()

  private var username: String = _
  private var pass: String = _

  object user {
    def name(n: String) {
      username = n
    }

    def password(p: String) {
      pass = p
    }

    def authenticate: Boolean = {
      textField("user").value = username

      webDriver.findElementById("pass").sendKeys(pass)

      click on "enter"

      eventually(timeout(Span(5, Seconds))) {
        assert(currentUrl contains ".html")
      }

//      Thread.sleep(5000)

      true
    }

    def logout: Boolean = {

      click on className("exit")

      eventually(timeout(Span(5, Seconds))) {
        assert(currentUrl contains ".do")
      }

//      Thread.sleep(5000)

      true
    }
  }

  after {
    println("After Inner")
    // Time to browser to finish its page loading
    Thread.sleep(5000)
  }

  override def afterAll {
    quit()
  }

}

