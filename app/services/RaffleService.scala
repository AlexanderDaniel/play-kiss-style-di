package services

import repositories.Member

import scala.concurrent.Future

trait RaffleService {
  def raffle: Future[Option[Member]]
}
