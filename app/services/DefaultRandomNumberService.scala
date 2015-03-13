package services

import scala.util.Random

class DefaultRandomNumberService(seed: Long) extends RandomNumberService {
  private val random = new Random(seed)

  override def nextLong: Long =
    random.nextLong()

  override def nextInt(maxExclusive: Int): Int =
    random.nextInt(maxExclusive)
}
