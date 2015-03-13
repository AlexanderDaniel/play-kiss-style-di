package repositories

class ThermalNoiseTrueRandomNumberRepo(usbId: String) extends TrueRandomNumberRepo {
  override def retrieve: Long = 42
}
