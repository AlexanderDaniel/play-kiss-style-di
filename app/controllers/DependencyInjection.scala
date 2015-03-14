package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits._
import services.BrainScanAnalyserService

class DependencyInjection(brainScanAnalyserService: BrainScanAnalyserService) extends Controller {

  def explain = Action.async {
    brainScanAnalyserService.isKnown("DI").map { isKnown =>
      if (isKnown) Ok(views.html.di()) else NotImplemented
    }
  }
}
