package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{Action, Controller}
import services.RaffleService

class Raffle(raffleService: RaffleService) extends Controller {

  def raffle = Action.async {
    raffleService.raffle.map { maybeWinner =>
      maybeWinner.map { winner =>
        Ok(s"And the winner is ${winner.name}")
      }.getOrElse {
        Ok(s"No members, no winner :-(")
      }

    }
  }
}
