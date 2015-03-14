import controllers.Raffle
import play.api.{Play, Logger}
import play.api.mvc.Controller
import repositories.{ThermalNoiseTrueRandomNumberRepo, MeetupMembersRepo}
import services.{DefaultRaffleService, DefaultRandomNumberService}
import play.api.Play.current

object Global extends play.api.GlobalSettings {

  private val injector = KissStyleDI

  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    Logger.info(s"Global.getControllerInstance for $controllerClass")
    injector.getInstance(controllerClass).getOrElse(super.getControllerInstance(controllerClass))
  }
}

object KissStyleDI {

  private def buildControllersViaConstructorInjection: Seq[Controller] = {
    val membersRepo = new MeetupMembersRepo(configString("meetup.apiKey"))
    val trueRandomNumberRepo = new ThermalNoiseTrueRandomNumberRepo(configString("thermalNoise.port"))

    val randomNumberService = new DefaultRandomNumberService(trueRandomNumberRepo.retrieve)
    val raffleService = new DefaultRaffleService(membersRepo, randomNumberService)

    Seq[Controller](
      new Raffle(raffleService)
    )
  }

  private def buildLookup(controllers: Seq[Controller]): Map[Class[_], Controller] =
    controllers.map(c => (c.getClass, c)).toMap

  /**
   * `lazy` because a running Play app is needed for retrieving configuration keys (The `Global` object
   * and hence `KissStyleDI` gets constructed before the Play app is running)
   */
  private lazy val lookup: Map[Class[_], Controller] =
    buildLookup(buildControllersViaConstructorInjection)

  def getInstance[A](controllerClass: Class[A]): Option[A] =
    lookup.get(controllerClass).map(_.asInstanceOf[A])

  private def configString(path: String): String =
    Play.configuration.getString(path).getOrElse(sys.error(s"Missing configuration key: $path"))
}