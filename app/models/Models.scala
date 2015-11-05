package models

object FuelType extends Enumeration {
  type fuelType = Value
  val SP95, SP98, Diesel = Value
}

case class Container(containerId: String, qty: Double, maxStorage: Double, fuelType: FuelType.Value)
case class Pump(pumpId: String, containerId: String)
case class Client(clientId: String)