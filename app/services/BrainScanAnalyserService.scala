package services

import repositories.BrainScannerRepo

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._

class BrainScanAnalyserService(brainScannerRepo: BrainScannerRepo) {
  def isKnown(term: String): Future[Boolean] =
    brainScannerRepo.scan.map { scan =>
      scan.forall(_ == Byte.MinValue)
    }
}
