package model.DTO

import play.api.libs.json.Json

case class ProductDTO(id: Long, categoryId: Long, name: String, desc: String, fullDesc: String, imageUrl: String, price: BigDecimal)

object ProductDTO {
  implicit val productFormat = Json.format[ProductDTO]
}

case class NewProductDTO(categoryId: Long, name: String, desc: String, fullDesc: String, imageUrl: String, price: BigDecimal)

object NewProductDTO {
  implicit val productFormat = Json.format[NewProductDTO]
}
