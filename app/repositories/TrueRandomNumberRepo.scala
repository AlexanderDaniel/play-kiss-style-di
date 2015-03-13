package repositories

/**
 * True random number from hardware random number generator
 *
 * [[http://en.wikipedia.org/wiki/Hardware_random_number_generator Hardware random number generator]]
 */
trait TrueRandomNumberRepo {
  def retrieve: Long
}
