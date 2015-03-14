package services

import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._

import org.mockito.Mockito._
import repositories.{Member, MembersRepo}
import play.api.test.Helpers._

import scala.concurrent.Future

class DefaultRaffleServiceSpec extends PlaySpec with MockitoSugar {

  "raffle" must {
    "choose member based on random number" in {
      val mockMembersRepo = mock[MembersRepo]
      when(mockMembersRepo.retrieveAll) thenReturn Future.successful(Seq(Member("a"), Member("b"), Member("c")))
      val mockRandomService = mock[RandomNumberService]
      when(mockRandomService.nextInt(3)) thenReturn 0
      val raffleService = new DefaultRaffleService(mockMembersRepo, mockRandomService)

      await(raffleService.raffle) mustBe Member("a")
    }

    "handle when there a no members" in {
      val mockMembersRepo = mock[MembersRepo]
      when(mockMembersRepo.retrieveAll) thenReturn Future.successful(Seq.empty)
      val mockRandomService = mock[RandomNumberService]
      val raffleService = new DefaultRaffleService(mockMembersRepo, mockRandomService)

      await(raffleService.raffle) mustBe None
    }
  }
}
