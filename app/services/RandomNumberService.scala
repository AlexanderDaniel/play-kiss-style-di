package services

trait RandomNumberService {
  def nextLong: Long
  def nextInt(maxExclusive: Int): Int
}
