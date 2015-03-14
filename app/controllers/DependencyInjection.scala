package controllers

import play.api.libs.concurrent.Promise
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.duration._

class DependencyInjection extends Controller {

  def explain = Action.async {
    Promise.timeout("", 3.seconds).map { _ =>
      Ok(views.html.di())
    }
  }
}
