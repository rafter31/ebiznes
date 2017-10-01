package DAO

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject

import model.{CartEntity, CartItemsEntity}
import play.api.db.slick.DatabaseConfigProvider

class CartDAO @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit executionContext: ExecutionContext) extends BaseDAO(dbConfigProvider) {

  import driver.api._

  def all(): Future[Seq[CartEntity]] = db.run(Cart.result)

  def create(cart: CartEntity): Future[CartEntity] = {
    val insertQuery = Cart returning Cart.map(_.id) into ((cart, id) => cart.copy(id = id))
    val action = insertQuery += cart

    db.run(action)
  }

  def findById(id: String): Future[Option[CartEntity]] = db.run(Cart.filter(_.UserId === id).result.headOption)



}