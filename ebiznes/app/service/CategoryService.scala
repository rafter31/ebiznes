package service

import javax.inject.Inject

import DAO.CategoryDAO
import model.DTO.{CategoryDTO, NewCategoryDTO}
import model.CategoryEntity

import scala.concurrent.{ExecutionContext, Future}

class CategoryService @Inject()(private val categoryDAO: CategoryDAO)
                               (implicit executionContext: ExecutionContext) {

  def getAll: Future[List[CategoryDTO]] = {
    val entities = categoryDAO.all()
    entities.map(_.map {
      e => CategoryDTO(id = e.id, name = e.name, shortDesc = e.shortDesc, fullDesc = e.fullDesc, imageUrl = e.imageUrl, topCategory = e.topCategory)
    }.toList)
  }

  def getTop: Future[List[CategoryDTO]] = {
    val entities = categoryDAO.findTop()
    entities.map(_.map {
      e => CategoryDTO(id = e.id, name = e.name, shortDesc = e.shortDesc, fullDesc = e.fullDesc, imageUrl = e.imageUrl, topCategory = e.topCategory)
    }.toList)
  }

  def get(id: Long): Future[Option[CategoryDTO]] = {
    val eventualMaybeCategoryEntity: Future[Option[CategoryEntity]] = categoryDAO.findById(id)
    eventualMaybeCategoryEntity.map { case (maybeEntity) =>
      maybeEntity match {
        case Some(e) => Option.apply(CategoryDTO(id = e.id, name = e.name, shortDesc = e.shortDesc, fullDesc = e.fullDesc, imageUrl = e.imageUrl, topCategory = e.topCategory))
        case None => Option.empty[CategoryDTO]
      }
    }
  }

  def create(newCategoryDTO: NewCategoryDTO): Future[CategoryDTO] = {
    val eventualCategoryEntity = categoryDAO.create(CategoryEntity(0, newCategoryDTO.name, newCategoryDTO.shortDesc, newCategoryDTO.fullDesc, newCategoryDTO.imageUrl, newCategoryDTO.topCategory))
    eventualCategoryEntity.map(e => CategoryDTO(e.id, e.name, shortDesc = e.shortDesc, fullDesc = e.fullDesc, imageUrl = e.imageUrl, topCategory = e.topCategory))
  }

  def delete(id: Long): Future[Unit] = {
    categoryDAO.delete(id)
  }

  def update(id: Long, newCategoryDTO: NewCategoryDTO): Future[Option[CategoryDTO]] = {
    val eventualMaybeCategoryEntity: Future[Option[CategoryEntity]] = categoryDAO.update(id, CategoryEntity(id, newCategoryDTO.name, newCategoryDTO.shortDesc, newCategoryDTO.fullDesc, newCategoryDTO.imageUrl, newCategoryDTO.topCategory))

    eventualMaybeCategoryEntity.map { case (maybeEntity) =>
      maybeEntity match {
        case Some(e) => Option.apply(CategoryDTO(id = e.id, name = e.name, shortDesc = e.shortDesc, fullDesc = e.fullDesc, imageUrl = e.imageUrl, topCategory = e.topCategory))
        case None => Option.empty[CategoryDTO]
      }
    }
  }

}
