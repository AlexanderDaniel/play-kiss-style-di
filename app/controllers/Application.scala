package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def resources = Action {
    Ok(views.html.resources())
  }

  def prosandcons = Action {
    Ok(views.html.prosandcons())
  }

  def twofour() = Action {
    Ok(views.html.twoFour())
  }

  def kiss() = Action {
    Ok(views.html.kiss())
  }
}