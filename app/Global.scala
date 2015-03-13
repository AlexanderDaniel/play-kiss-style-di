import controllers.Raffle
import play.api.{Play, Logger}
import play.api.mvc.Controller
import repositories.{ThermalNoiseTrueRandomNumberRepo, MeetupMembersRepo}
import services.DefaultRandomNumberService
import play.api.Play.current

object Global extends play.api.GlobalSettings {

  private val injector = KissStyleDI

  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    Logger.warn(s"Global.getControllerInstance for $controllerClass")
    injector.getInstance(controllerClass)
      .getOrElse(super.getControllerInstance(controllerClass))
  }
}

object KissStyleDI {
  val meetupApiKey = "dasdas" // TODO Play.configuration.getString("meetup.apiKey").getOrElse(???)
  val membersRepo = new MeetupMembersRepo(meetupApiKey)

  val trueRandomNumberRepo = new ThermalNoiseTrueRandomNumberRepo("usb:0x23")

  val randomNumberService = new DefaultRandomNumberService(trueRandomNumberRepo.retrieve)

  val controllers = Seq[Controller](
    new Raffle(membersRepo, randomNumberService)
  )
  val lookup: Map[Class[_], Controller] = controllers.map(c => (c.getClass, c))
    .toMap

  def getInstance[A](controllerClass: Class[A]): Option[A] = {
    lookup.get(controllerClass).map(_.asInstanceOf[A])
  }
}