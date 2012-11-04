trait Logger {

  def log(msg: String) {
    println("C:" + msg)
  }
}

trait FileLogger extends Logger {

  override def log(msg: String) {
    println("F:" + msg)
  }
}

class LoggerImpl extends Logger {

  override def log(msg: String) {
    println(msg + ";")
  }
}

class ShowLog extends Logger {

  def show {
    log("Hello")
  }
}


class Fraction(val n: Int, val m: Int) {
  def *(a: Fraction) = n * m * a.n * a.m
}

object Fraction {
  def apply(n: Int, m: Int) = new Fraction(n, m)
  def unapply(f: Fraction) = {
    if (f.m == 0)
      None
    else
      Some(0)
  }
}

