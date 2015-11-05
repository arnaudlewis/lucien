package models

import play.api.Logger

import reactivemongo.bson._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson.BSONDocument

import reactivemongo.api.collections.bson.BSONCollection
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Container(_id: BSONObjectID, qty: Double, maxStorage: Double, fuelType: String) {
  def id : String = _id.stringify
}

object Container {
  implicit val containerBsonReader: BSONDocumentReader[Container] = Macros.reader[Container]
  implicit val containerBsonWriter: BSONDocumentWriter[Container] = Macros.writer[Container]

  implicit val containerJsonReader : Reads[Container] =
      (
        (__ \ "id").read[String] and
        (__ \ "qty").read[Double] and
        (__ \ "maxStorage").read[Double] and
        (__ \ "fuelType").read[String]
      )(Container.toObject _)

  implicit val containerJsonWrite : OWrites[Container] = OWrites { c =>
    Json.obj (
      "id" -> c.id,
      "qty" -> c.qty,
      "maxStorage" -> c.maxStorage,
      "fuelType" -> c.fuelType
    )
  }

  def toObject(id: String, qty: Double, maxStorage: Double, fuelType: String) = {
    Container(BSONObjectID(id), qty, maxStorage, fuelType)
  }

  def fromJson(containerAsJson: JsValue) : Option[Container] = {
    containerJsonReader.reads(containerAsJson) match {
      case JsSuccess(container, _) =>
        Some(container)
      case e: JsError =>
        Logger.warn("this is not a valid container " + e)
        None
    }
  }

  def toJson(container: Container) : JsValue = {
    containerJsonWrite.writes(container)
  }
}

case class Pump(pumpId: String, containerId: String)
case class Client(clientId: String)