package model.DTO

import play.api.libs.json.Json

case class OrderItemDTO(id: Long, orderId: Long, productId: Long, quantity: Int, name: String, price: BigDecimal)

object OrderItemDTO {
  implicit val orderItemFormat = Json.format[OrderItemDTO]
}

case class OrderDTO(id: Long, comment: String)

object OrderDTO {
  implicit val orderFormat = Json.format[OrderDTO]
}

case class DetailsOrderDTO(id: Long, comment: String, items: Seq[OrderItemDTO])

object DetailsOrderDTO {
  implicit val orderFormat = Json.format[DetailsOrderDTO]
}

case class NewOrderItemDTO(productId: Long, quantity: Int)

object NewOrderItemDTO {
  implicit val newOrderItemFormat = Json.format[NewOrderItemDTO]
}

case class NewOrderDTO(comment: String, items: Seq[NewOrderItemDTO])

object NewOrderDTO {
  implicit val newOrderFormat = Json.format[NewOrderDTO]
}

