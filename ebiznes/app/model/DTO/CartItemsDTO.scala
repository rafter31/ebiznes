package model.DTO

import play.api.libs.json.Json

case class CartItemsDTO(id: Int, ProductId: Int, Quantity: Int, CartId: Int, Name: String, GrossAmount: Double, UnitAmount: Double, IsPayed: Int)

object CartItemsDTO {
  implicit val cartItemsFormat = Json.format[CartItemsDTO]
}

case class NewCartItemsDTO(ProductId: Int, Quantity: Int, CartId: Int, Name: String, GrossAmount: Double, UnitAmount: Double, IsPayed: Int)

object NewCartItemsDTO {
  implicit val cartItemsFormat = Json.format[NewCartItemsDTO]
}
