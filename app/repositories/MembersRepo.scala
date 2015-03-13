package repositories

import scala.concurrent.Future

trait MembersRepo {
  def retrieveAll: Future[Seq[Member]]
}
