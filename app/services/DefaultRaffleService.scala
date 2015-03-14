package services

import repositories.{MembersRepo, Member}
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future

class DefaultRaffleService(membersRepo: MembersRepo, random: RandomNumberService) extends RaffleService {
  override def raffle: Future[Option[Member]] =
    membersRepo.retrieveAll.map { members =>
      if (members.nonEmpty)
        Some(members(random.nextInt(members.size)))
      else
        None
    }
}
