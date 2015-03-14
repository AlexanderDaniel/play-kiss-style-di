package repositories

import play.api.libs.concurrent.Promise

import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits._

class BrainScannerRepo {
  def scan: Future[Seq[Byte]] =
    Promise.timeout("scanning", 2.seconds).map { _ =>
      Array.fill(256)(Byte.MinValue)
    }
}
