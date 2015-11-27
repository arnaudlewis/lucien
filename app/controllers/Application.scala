package controllers

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

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._

import play.api.Play.current
import play.api.i18n.Messages.Implicits._

import org.joda.time.DateTime

import play.api.libs.functional._
import play.api.libs.functional.syntax._

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs._
import play.api.libs.json._
import reactivemongo.bson._
import scala.util.{ Failure, Success }


import models.{Client, Container, Pump, Fuel, Repo}

class Application extends Controller {

  def index(url: String) = Action {
    Ok(views.html.infos())
  }

  def changeFuelStockForm = Form (
    tuple (
      "containerId" -> bigDecimal,
      "qty" -> bigDecimal
    )
  )
  def changeFuelStock = Action.async { implicit request =>
    changeFuelStockForm.bindFromRequest.fold (
      {
        case errors =>
          Future.successful(BadRequest(Json.obj("errors" -> errors.errorsAsJson)))
      },
      {
        case (containerId, qty) =>
          Repo.changeStock(containerId.toInt, qty.doubleValue())
          Future.successful(Ok)
      }
    )
  }

  def priceForm = Form (
    tuple (
      "pumpId" -> bigDecimal,
      "qty" -> bigDecimal,
      "fuelType" -> text.verifying(nonEmpty)
    )
  )
  def price = Action.async { implicit request =>
    priceForm.bindFromRequest.fold (
      {
        case errors =>
          Future.successful(BadRequest(Json.obj("errors" -> errors.errorsAsJson)))
      }, 
      {
        case (pumpId, qty, fuelType) =>
        val price = Repo.getPrice(qty.toInt, fuelType)
          Future.successful(Ok(Json.obj("pump" -> pumpId.toString, "price" -> price)))
      }
    )
  }

  def containersStatus = Action.async { implicit request =>
    Future.successful(Ok(Repo.status))
  }

  def toggelContainerStatusForm = Form (
    "containerId" -> text.verifying(nonEmpty)
  )

  def toggelContainerStatus = Action.async { implicit request =>
    toggelContainerStatusForm.bindFromRequest.fold (
    {
      case errors =>
        Future.successful(BadRequest(Json.obj("errors" -> errors.errorsAsJson)))
    }, 
    {
      case(containerId) =>
        Repo.changeContainerStatus(containerId.toInt)
        Future.successful(Ok)
    }
    )
  }
}
