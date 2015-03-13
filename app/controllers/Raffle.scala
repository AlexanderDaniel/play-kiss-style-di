package controllers

import play.api.mvc.{Action, Controller}
import repositories.MembersRepo
import services.RandomNumberService
import play.api.libs.concurrent.Execution.Implicits._

class Raffle(membersRepo: MembersRepo, random: RandomNumberService) extends Controller {

  def raffle = Action.async {
    membersRepo.retrieveAll.map { members =>
      val winner = members(random.nextInt(members.size))
      Ok(s"And the winner is ${winner.name}")
    }
  }
}
