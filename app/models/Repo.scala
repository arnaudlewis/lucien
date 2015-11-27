// case class Container(id: Int, qty: Double, maxStorage: Double, fuelType: String, status: Boolean)
// case class Pump(pumpId: Int, containerId: String)
// case class Client(clientId: Int)
// case class Fuel(fuelType: String, priceLiter: double)

package models

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.Logger

import reactivemongo.bson._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.bson.BSONCollection
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Repo {

  var containers : Seq[Container] = getContainers
  var pumps: Seq[Pump] = getPumps
  var clients: Seq[Client] = getClients

  def getContainers : Seq[Container] = {
    return Seq(
      Container(1, 2500, 5000, "SP95", true),
      Container(2, 3000.45, 5000, "SP98", true),
      Container(3, 2366.76, 5000, "GAZOLE", true)
    )
  }

  def getPumps : Seq[Pump] = {
    return Seq(
      Pump(1, 1),
      Pump(2, 2),
      Pump(3, 2),
      Pump(4, 1),
      Pump(5, 3),
      Pump(6, 1),
      Pump(7, 3),
      Pump(8, 3),
      Pump(9, 3),
      Pump(10, 2),
      Pump(11, 1)
    )
  }

  def getClients : Seq[Client] = {
    return Seq(
      Client(1)
    )
  }

  def getFuelPrices: Seq[Fuel] = {
    return Seq(
      Fuel("SP95", 1.45),
      Fuel("SP98", 1.55),
      Fuel("GAZOLE", 1.25)
    )
  }

  def changeStock(containerId: Int, qty: Double): Unit = {
    val maybeContainer = containers.find(_.id == containerId)

    val index = containers.indexWhere(_.id == containerId)
    maybeContainer.map { c =>
      val container = Container(c.id, c.qty + qty, c.maxStorage, c.fuelType, c.status)
      println(index)
      containers = containers.updated(index, container)
      println(containers)
    }
  }

  def status : String = {
    containers.map { container =>
      val status = if(container.status) "Activated" else "Deactivated"
      container.id.toString + " " + container.qty.toString + " " + container.maxStorage + " " + container.fuelType + " " + status + "\n"
    }.mkString("\n")
  }

// case class Container(id: Int, qty: Double, maxStorage: Double, fuelType: String, status: Boolean)

  def changeContainerStatus(containerId: Int): Unit = {
    val maybeContainer = containers.find(_.id == containerId)
    val index = containers.indexWhere(_.id == containerId)
    val container = maybeContainer.map { c =>
      val container = Container(c.id, c.qty, c.maxStorage, c.fuelType, !c.status)
      containers = containers.updated(index, container)
    }
    
  }

  def getPrice(qty: Double, fuelType: String): Double = {
    val price = getFuelPrices.find(_.fuelType == fuelType).map(_.price).getOrElse(throw new RuntimeException("Bad fueltype"))
    qty * price
  }
}