package models

import play.api.Logger

import reactivemongo.bson._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson.BSONDocument

import reactivemongo.api.collections.bson.BSONCollection
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Container(id: Int, qty: Double, maxStorage: Double, fuelType: String, status: Boolean)
case class Pump(pumpId: Int, containerId: Int)
case class Client(clientId: Int)
case class Fuel(fuelType: String, price: Double)