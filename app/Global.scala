import controllers.Raffle
import play.api.Logger
import play.api.mvc.Controller

object Global extends play.api.GlobalSettings {

  object Global extends play.api.GlobalSettings {

    private val injector = KissStyleDI

    override def getControllerInstance[A](controllerClass: Class[A]): A = {
      Logger.info(s"Global.getControllerInstance for $controllerClass")
      injector.getInstance(controllerClass)
        .getOrElse(super.getControllerInstance(controllerClass))
    }
  }
}

object KissStyleDI {
  val controllers = Seq[Controller](
    new Raffle
  )
  val lookup: Map[Class[_], Controller] = controllers.map(c => (c.getClass, c))
    .toMap

  def getInstance[A](controllerClass: Class[A]): Option[A] = {
    lookup.get(controllerClass).map(_.asInstanceOf[A])
  }
}