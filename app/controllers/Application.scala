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

import models.{Client, Container, Pump}

class Application extends Controller {

  def index(url: String) = Action {
    Ok(views.html.infos())
  }

  def changeFuelStockForm = Form (
    tuple (
      "containerId" -> text.verifying(nonEmpty),
      "qty" -> bigDecimal
    )
  )
  def changeFuelStock = Action.async {
    Future.successful(Ok)
  }

  def priceForm = Form (
    tuple (
      "pumpId" -> text.verifying(nonEmpty),
      "qty" -> bigDecimal,
      "fuelType" -> text.verifying(nonEmpty)
    )
  )
  def price = Action.async {
    //return priceForm
    Future.successful(Ok)
  }

  def containersStatus = Action.async {
    //     numero de cuve
    // type d'essence
    // quantité dispo
    // statut
    Future.successful(Ok)
  }

  def toggelContainerStatusForm = Form (
    "containerId" -> text.verifying(nonEmpty)
  )
  def toggelContainerStatus = Action.async {
    Future.successful(Ok)
  }

  def paymentHistoryForm = Form (
    "clientId" -> text.verifying(nonEmpty)
  )
  def paymentHistory = Action.async {
    //generate few ids to try it and avoid client creation
    Future.successful(Ok)
  }
}
