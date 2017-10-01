package model.DTO

import play.api.libs.json.Json

case class CartDTO(id: Int, UserId: String)

object CartDTO {
  implicit val cartFormat = Json.format[CartDTO]
}

case class NewCartDTO(id: Int, UserId: String)

object NewCartDTO {
  implicit val cartFormat = Json.format[NewCartDTO]
}
