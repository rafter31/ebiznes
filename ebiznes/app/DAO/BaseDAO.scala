package DAO

import javax.inject.Inject

import model.{CategoryEntity, ProductEntity, CartItemsEntity,CartEntity}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext

class BaseDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  protected val Categories = TableQuery[CategoryTable]
  protected val Products = TableQuery[ProductTable]
  protected val CartItems = TableQuery[CartItemsTable]
  protected val Cart = TableQuery[CartTable]

  protected class CategoryTable(tag: Tag) extends Table[CategoryEntity](tag, "Category") {
    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
    def name = column[String]("name")
    def shortDesc = column[String]("short_desc")
    def fullDesc = column[String]("full_desc")
    def imageUrl = column[String]("image_url")
    def topCategory = column[Boolean]("top_category")

    override def * = (id, name, shortDesc, fullDesc, imageUrl, topCategory) <> (CategoryEntity.tupled, CategoryEntity.unapply)
  }

  protected class ProductTable(tag: Tag) extends Table[ProductEntity](tag, "Product") {
    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
    def name = column[String]("name")
    def desc = column[String]("desc")
    def price = column[BigDecimal]("price")
    def fullDesc = column[String]("full_desc")
    def imageUrl = column[String]("image_url")
    def categoryId = column[Long]("category_id")

    override def * = (id, categoryId, name, desc, fullDesc, imageUrl, price) <> (ProductEntity.tupled, ProductEntity.unapply)

    def category = foreignKey("fk_product_category", categoryId, Categories)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }



  protected class CartItemsTable(tag: Tag) extends Table[CartItemsEntity](tag, "CartItems") {
    def id = column[Int]("id", O.AutoInc, O.PrimaryKey)
    def ProductId = column[Int]("ProductId")
    def Quantity = column[Int]("Quantity")
    def CartId = column[Int]("CartId")
    def Name = column[String]("Name")
    def GrossAmount = column[Double]("GrossAmount")
    def UnitAmount = column[Double]("UnitAmount")
    def IsPayed = column[Int]("IsPayed")

    override def * = (id, ProductId, Quantity, CartId, Name, GrossAmount, UnitAmount,IsPayed) <> (CartItemsEntity.tupled, CartItemsEntity.unapply)
    // id: Int, ProductId: Int, Quantity: Int, CartId: Int, Name: String, GrossAmount: Double, UnitAmount: Double, IsPayed: Int
    // def category = foreignKey("fk_product_category", categoryId, Categories)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  protected class CartTable(tag: Tag) extends Table[CartEntity](tag, "Cart") {
    def id = column[Int]("id", O.AutoInc, O.PrimaryKey)
    def UserId = column[String]("UserId")

    override def * = (id, UserId) <> (CartEntity.tupled, CartEntity.unapply)
  }


}
