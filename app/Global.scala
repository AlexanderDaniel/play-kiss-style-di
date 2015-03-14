import controllers.{DependencyInjection, Raffle}
import play.api.{Play, Logger}
import play.api.mvc.Controller
import repositories.{BrainScannerRepo, ThermalNoiseTrueRandomNumberRepo, MeetupMembersRepo}
import services.{BrainScanAnalyserService, DefaultRaffleService, DefaultRandomNumberService}
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
    val brainScannerRepo = new BrainScannerRepo

    val randomNumberService = new DefaultRandomNumberService(trueRandomNumberRepo.retrieve)
    val raffleService = new DefaultRaffleService(membersRepo, randomNumberService)
    val brainAnalyserService = new BrainScanAnalyserService(brainScannerRepo)

    Seq[Controller](
      new Raffle(raffleService),
      new DependencyInjection(brainAnalyserService)
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