package service

import javax.inject.Inject

import model.DTO.{NewProductDTO, ProductDTO, NewCartDTO, CartDTO,CartItemsDTO, NewCartItemsDTO}
import DAO.ProductDAO
import DAO.CartDAO
import DAO.CartItemsDAO
import model.ProductEntity
import model.CartItemsEntity
import model.CartEntity
import scala.concurrent.{ExecutionContext, Future}

class ProductsService @Inject()(private val productDAO: ProductDAO, private val cartDAO: CartDAO, private val cartItemsDAO: CartItemsDAO)
                               (implicit executionContext: ExecutionContext) {

  def getAll: Future[List[ProductDTO]] = {
    val entities = productDAO.all()
    entities.map(_.map {
      e => ProductDTO(id = e.id, categoryId = e.categoryId, name = e.name, desc = e.desc, fullDesc = e.fullDesc, imageUrl = e.imageUrl, price = e.price)
    }.toList)
  }

  def get(id: Long): Future[Option[ProductDTO]] = {
    val eventualMaybeProductEntity: Future[Option[ProductEntity]] = productDAO.findById(id)
    eventualMaybeProductEntity.map { case (maybeEntity) =>
      maybeEntity match {
        case Some(e) => Option.apply(ProductDTO(id = e.id, categoryId = e.categoryId, name = e.name, desc = e.desc, fullDesc = e.fullDesc, imageUrl = e.imageUrl, price = e.price))
        case None => Option.empty[ProductDTO]
      }
    }
  }

  def getByCategory(categoryId: Long): Future[List[ProductDTO]] = {
    val entities = productDAO.findByCategoryId(categoryId)
    entities.map(_.map {
      e => ProductDTO(id = e.id, categoryId = e.categoryId, name = e.name, desc = e.desc, fullDesc = e.fullDesc, imageUrl = e.imageUrl, price = e.price)
    }.toList)
  }

  def create(newProductDTO: NewProductDTO): Future[ProductDTO] = {
    val eventualProductEntity = productDAO.create(ProductEntity(0, newProductDTO.categoryId, newProductDTO.name, newProductDTO.desc, newProductDTO.fullDesc, newProductDTO.imageUrl, newProductDTO.price))
    eventualProductEntity.map(e => ProductDTO(e.id, e.categoryId, e.name, e.desc, e.fullDesc, e.imageUrl, e.price))
  }

  def delete(id: Long): Future[Unit] = {
    productDAO.delete(id)
  }

  def update(id: Long, newProductDTO: NewProductDTO): Future[Option[ProductDTO]] = {
    val eventualMaybeProductEntity: Future[Option[ProductEntity]] = productDAO.update(id, ProductEntity(id, newProductDTO.categoryId, newProductDTO.name, newProductDTO.desc, newProductDTO.fullDesc, newProductDTO.imageUrl, newProductDTO.price))

    eventualMaybeProductEntity.map { case (maybeEntity) =>
      maybeEntity match {
        case Some(e) => Option.apply(ProductDTO(id = e.id, categoryId = e.categoryId, name = e.name, desc = e.desc, fullDesc = e.fullDesc, imageUrl = e.imageUrl, price = e.price))
        case None => Option.empty[ProductDTO]
      }
    }
  }

  def getCart: Future[List[CartDTO]] = {
    val entities = cartDAO.all()
    entities.map(_.map {
      e => CartDTO(id = e.id, UserId = e.UserId)
    }.toList)
  }
  def getCartItems: Future[List[CartItemsDTO]] = {
    val entities = cartItemsDAO.all()
    entities.map(_.map {
      e => CartItemsDTO(id = e.id, ProductId = e.ProductId, Quantity=e.Quantity, CartId = e.CartId,Name=e.Name, GrossAmount=e.GrossAmount, UnitAmount =e.UnitAmount, IsPayed =e.IsPayed)
    }.toList)
  }


  def createCartItems(newCartItemsDTO: NewCartItemsDTO): Future[CartItemsDTO] = {
    val eventualcartItemsEntity = cartItemsDAO.create(CartItemsEntity(0, newCartItemsDTO.ProductId, newCartItemsDTO.Quantity, newCartItemsDTO.CartId, newCartItemsDTO.Name, newCartItemsDTO.GrossAmount, newCartItemsDTO.UnitAmount, newCartItemsDTO.IsPayed))
    eventualcartItemsEntity.map(e => CartItemsDTO(e.id, e.ProductId, e.Quantity, e.CartId, e.Name, e.GrossAmount, e.UnitAmount,e.IsPayed))
  }

  def deleteCartItems(id: Int): Future[Unit] = {
    cartItemsDAO.delete(id)
  }
  def realizeOrder(): Future[Unit] = {
    cartItemsDAO.realizeOrder()
  }

  def createCart(newCartDTO: NewCartDTO): Future[CartDTO] = {
    val eventualCartEntity = cartDAO.create(CartEntity(0, newCartDTO.UserId))
    eventualCartEntity.map(e => CartDTO(e.id, e.UserId))
}
  def getCartByUserId(id: String): Future[Option[CartDTO]] = {
    val eventualMaybeCartEntity: Future[Option[CartEntity]] = cartDAO.findById(id)
    eventualMaybeCartEntity.map { case (maybeEntity) =>
      maybeEntity match {
        case Some(e) => Option.apply(CartDTO(id = e.id, UserId = e.UserId))
        case None => Option.empty[CartDTO]
      }
    }
  }


  def getCartItemsByUserId(UserId: Int): Future[List[CartItemsDTO]] = {
    val entities = cartItemsDAO.findByCartId(UserId)
    entities.map(_.map {
      e => CartItemsDTO(id = e.id, ProductId = e.ProductId, Quantity = e.Quantity, CartId = e.CartId, Name = e.Name, GrossAmount = e.GrossAmount, UnitAmount = e.UnitAmount, IsPayed=e.IsPayed)
    }.toList)
  }




}
