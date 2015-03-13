package repositories

import scala.concurrent.Future

class MeetupMembersRepo(apiKey: String) extends MembersRepo {
  override def retrieveAll: Future[Seq[Member]] = faked

  private def faked = Future.successful(Seq(
    Member("Dominik Gruber"),
    Member("Oleg Rudenko"),
    Member("Alexander Daniel")
  ))

}
