package controllers

import javax.inject.Inject

import model.DTO.{NewProductDTO, ProductDTO, CartDTO, NewCartDTO, NewCartItemsDTO, CartItemsDTO}
import play.api.mvc.{Action, AnyContent, Controller}
import play.api.libs.json.Json
import service._


import scala.concurrent.{ExecutionContext, Future}

class
ProductController @Inject()(private val productsService: ProductsService)
                           (implicit executionContext: ExecutionContext) extends Controller {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val eventualProductDTOs: Future[List[ProductDTO]] = productsService.getAll

    eventualProductDTOs.map(
      p => Ok(Json.toJson(p))
    )
  }

  def get(id: Long): Action[AnyContent] = Action.async { implicit request =>
    val eventualMaybeProductDTO: Future[Option[ProductDTO]] = productsService.get(id)

    eventualMaybeProductDTO.map { case (maybeProductDTO) =>
      maybeProductDTO match {
        case Some(productDTO) => Ok(Json.toJson(productDTO))
        case None => NotFound
      }
    }
  }

  def getProducts(categoryId: Long): Action[AnyContent] = Action.async { implicit request =>
    val eventualProductDTOs: Future[List[ProductDTO]] = productsService.getByCategory(categoryId)

    eventualProductDTOs.map(
      p => Ok(Json.toJson(p))
    )
  }

  def create: Action[AnyContent] = Action.async { implicit request =>
    val newProductDTO: NewProductDTO = request.body.asJson.get.as[NewProductDTO]
    val eventualProductDTO: Future[ProductDTO] = productsService.create(newProductDTO)

    eventualProductDTO.map { case (productDTO) => Ok(Json.toJson(productDTO))
    }
  }

  def delete(id: Long) = Action { implicit request =>
    productsService.delete(id)
    Redirect(routes.Application.index())
  }

  def update(id: Long): Action[AnyContent] = Action.async { implicit request =>
    val newProductDTO: NewProductDTO = request.body.asJson.get.as[NewProductDTO]
    val eventualMaybeProductDTO: Future[Option[ProductDTO]] = productsService.update(id, newProductDTO)

    eventualMaybeProductDTO.map { case (maybeProductDTO) =>
      maybeProductDTO match {
        case Some(productDTO) => Ok(Json.toJson(productDTO))
        case None => NotFound
      }
    }
  }

  def getCart: Action[AnyContent] = Action.async { implicit request =>
    val eventualCartDTOs: Future[List[CartDTO]] = productsService.getCart

    eventualCartDTOs.map(
      p => Ok(Json.toJson(p))
    )
  }

  def getCartItems: Action[AnyContent] = Action.async { implicit request =>
    val eventualCartItemsDTOs: Future[List[CartItemsDTO]] = productsService.getCartItems

    eventualCartItemsDTOs.map(
      p => Ok(Json.toJson(p))
    )
  }

  def createCartItems: Action[AnyContent] = Action.async { implicit request =>
    val test = request.body
    val newCartItemsDTO: NewCartItemsDTO = request.body.asJson.get.as[NewCartItemsDTO]
    val eventualCartItemsDTO: Future[CartItemsDTO] = productsService.createCartItems(newCartItemsDTO)

    eventualCartItemsDTO.map { case (cartItemsDTO) => Ok(Json.toJson(cartItemsDTO))
    }
  }


  def deleteCartItems(id: Int) = Action { implicit request =>
    productsService.deleteCartItems(id)
    Redirect(routes.Application.index())
  }


  def realizeOrder() = Action { implicit request =>
    productsService.realizeOrder()
    Redirect(routes.Application.index())
  }

  def createCart: Action[AnyContent] = Action.async { implicit request =>
    val test = request.body
    val newCartDTO: NewCartDTO = request.body.asJson.get.as[NewCartDTO]
    val eventualCartDTO: Future[CartDTO] = productsService.createCart(newCartDTO)

    eventualCartDTO.map { case (cartDTO) => Ok(Json.toJson(cartDTO))
    }
  }



  def getCartByUserId(id: String): Action[AnyContent] = Action.async { implicit request =>
    val eventualMaybeCartDTO: Future[Option[CartDTO]] = productsService.getCartByUserId(id)

    eventualMaybeCartDTO.map { case (maybeCartDTO) =>
      maybeCartDTO match {
        case Some(cartDTO) => Ok(Json.toJson(cartDTO))
        case None => NotFound
      }
    }
  }



  def getCartItemsByCartId(userId: Int): Action[AnyContent] = Action.async { implicit request =>
    val eventualCartItemsDTOs: Future[List[CartItemsDTO]] = productsService.getCartItemsByUserId(userId)

    eventualCartItemsDTOs.map(
      p => Ok(Json.toJson(p))
    )
  }



}
