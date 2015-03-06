package controllers

import play.api.mvc.{Action, Controller}

class Raffle extends Controller {

  def raffle = Action {
    Ok("KISS-style DI")
  }
}
