package DAO

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject

import model.ProductEntity
import play.api.db.slick.DatabaseConfigProvider

class ProductDAO @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
                          (implicit executionContext: ExecutionContext) extends BaseDAO(dbConfigProvider) {

  import driver.api._

  def all(): Future[Seq[ProductEntity]] = db.run(Products.result)

  def findById(id: Long): Future[Option[ProductEntity]] = db.run(Products.filter(_.id === id).result.headOption)

  def findByCategoryId(categoryId: Long): Future[Seq[ProductEntity]] = db.run(Products.filter(_.categoryId === categoryId).result)

  def create(product: ProductEntity): Future[ProductEntity] = {
    val insertQuery = Products returning Products.map(_.id) into ((product, id) => product.copy(id = id))
    val action = insertQuery += product
    db.run(action)
  }

  def delete(id: Long): Future[Unit] = db.run(Products.filter(_.id === id).delete).map(_ => ())

  def update(id: Long, product: ProductEntity): Future[Option[ProductEntity]] = {
    val productToUpdate: ProductEntity = product.copy(id)
    db.run(Products.filter(_.id === id).update(productToUpdate)).map {
      case 0 => None
      case _ => Some(productToUpdate)
    }
  }

}