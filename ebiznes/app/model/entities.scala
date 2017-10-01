package model

case class ProductEntity(id: Long, categoryId: Long, name: String, desc: String, fullDesc: String, imageUrl: String, price: BigDecimal)

case class CategoryEntity(id: Long, name: String, shortDesc: String, fullDesc: String, imageUrl: String, topCategory: Boolean)







case class CartItemsEntity(id: Int, ProductId: Int, Quantity: Int, CartId: Int, Name: String, GrossAmount: Double, UnitAmount: Double, IsPayed: Int)

case class CartEntity(id: Int, UserId: String)