package DAO

import javax.inject.Inject

import model.CategoryEntity
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}

class CategoryDAO @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit executionContext: ExecutionContext) extends BaseDAO(dbConfigProvider) {

  import driver.api._

  def all(): Future[Seq[CategoryEntity]] = db.run(Categories.result)

  def findTop(): Future[Seq[CategoryEntity]] = {
    db.run(Categories.filter(_.topCategory === true).result)
  }

  def findById(id: Long): Future[Option[CategoryEntity]] = db.run(Categories.filter(_.id === id).result.headOption)

  def create(category: CategoryEntity): Future[CategoryEntity] = {
    val insertQuery = Categories returning Categories.map(_.id) into ((category, id) => category.copy(id = id))
    val action = insertQuery += category
    db.run(action)
  }

  def delete(id: Long): Future[Unit] = db.run(Categories.filter(_.id === id).delete).map(_ => ())

  def update(id: Long, category: CategoryEntity): Future[Option[CategoryEntity]] = {
    val categoryToUpdate: CategoryEntity = category.copy(id)
    db.run(Categories.filter(_.id === id).update(categoryToUpdate)).map {
      case 0 => None
      case _ => Some(categoryToUpdate)
    }
  }

}