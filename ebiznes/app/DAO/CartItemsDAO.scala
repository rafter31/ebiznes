package DAO

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject
import model.DTO.{NewProductDTO, ProductDTO, NewCartDTO, CartDTO,CartItemsDTO, NewCartItemsDTO}
import model.CartItemsEntity
import play.api.db.slick.DatabaseConfigProvider

class CartItemsDAO @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
                          (implicit executionContext: ExecutionContext) extends BaseDAO(dbConfigProvider) {

  import driver.api._

  def all(): Future[Seq[CartItemsEntity]] = db.run(CartItems.result)

  //def create(cartItems: CartItemsEntity): Future[CartItemsEntity] = {
  // val insertQuery = CartItems returning CartItems.map(_.id) into ((cartItems, id) => cartItems.copy(id = id))
  //val action = insertQuery += cartItems
  //db.run(action)
  // }

  def create(cartItems: CartItemsEntity): Future[CartItemsEntity] = {
    val insertQuery = CartItems returning CartItems.map(_.id) into ((cartItems, id) => cartItems.copy(id = id))
    val action = insertQuery += cartItems
    db.run(action)

  }

def delete(id: Int): Future[Unit] = db.run(CartItems.filter(_.id === id).delete).map(_ => ())


  def realizeOrder(): Future[Unit] ={
   // val cartItems = db.run(CartItems.result)
    db.run(CartItems.delete).map(_ => ())


  }


  def findByCartId(CartId: Int): Future[Seq[CartItemsEntity]] = db.run(CartItems.filter(_.CartId === CartId).result)
}