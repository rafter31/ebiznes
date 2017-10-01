package controllers

import javax.inject.Inject

import model.DTO.{CategoryDTO, NewCategoryDTO}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}
import service.CategoryService

import scala.concurrent.{ExecutionContext, Future}

class CategoryController @Inject()(private val categoryService: CategoryService)
                                  (implicit executionContext: ExecutionContext) extends Controller {

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    val eventualCategoryDTOs: Future[List[CategoryDTO]] = categoryService.getAll

    eventualCategoryDTOs.map(
      c => Ok(Json.toJson(c))
    )
  }

  def getTop: Action[AnyContent] = Action.async { implicit request =>
    val eventualCategoryDTOs: Future[List[CategoryDTO]] = categoryService.getTop

    eventualCategoryDTOs.map(
      c => Ok(Json.toJson(c))
    )
  }

  def get(id: Long): Action[AnyContent] = Action.async { implicit request =>
    val eventualMaybeCategoryDTO: Future[Option[CategoryDTO]] = categoryService.get(id)

    eventualMaybeCategoryDTO.map { case (maybeCategoryDTO) =>
      maybeCategoryDTO match {
        case Some(categoryDTO) => Ok(Json.toJson(categoryDTO))
        case None => NotFound
      }
    }
  }

  def create: Action[AnyContent] = Action.async { implicit request =>
    val newCategoryDTO: NewCategoryDTO = request.body.asJson.get.as[NewCategoryDTO]
    val eventualCategoryDTO: Future[CategoryDTO] = categoryService.create(newCategoryDTO)

    eventualCategoryDTO.map { case (categoryDTO) => Ok(Json.toJson(categoryDTO))
    }
  }

  def delete(id: Long) = Action { implicit request =>
    categoryService.delete(id)
    Redirect(routes.Application.index())
  }

  def update(id: Long): Action[AnyContent] = Action.async { implicit request =>
    val newCategoryDTO: NewCategoryDTO = request.body.asJson.get.as[NewCategoryDTO]
    val eventualMaybeCategoryDTO: Future[Option[CategoryDTO]] = categoryService.update(id, newCategoryDTO)

    eventualMaybeCategoryDTO.map { case (maybeCategoryDTO) =>
      maybeCategoryDTO match {
        case Some(categoryDTO) => Ok(Json.toJson(categoryDTO))
        case None => NotFound
      }
    }
  }

}
