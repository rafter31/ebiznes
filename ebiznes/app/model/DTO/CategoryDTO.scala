package model.DTO

import play.api.libs.json.Json

case class CategoryDTO(id: Long, name: String, shortDesc: String, fullDesc: String, imageUrl: String, topCategory: Boolean)

object CategoryDTO {
  implicit val categoryFormat = Json.format[CategoryDTO]
}

case class NewCategoryDTO(name: String, shortDesc: String, fullDesc: String, imageUrl: String, topCategory: Boolean)

object NewCategoryDTO {
  implicit val categoryFormat = Json.format[NewCategoryDTO]
}
