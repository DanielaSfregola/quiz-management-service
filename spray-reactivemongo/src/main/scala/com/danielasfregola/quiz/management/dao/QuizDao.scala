package com.danielasfregola.quiz.management.dao

import com.danielasfregola.quiz.management.model.persistence.QuizEntity
import reactivemongo.api.QueryOpts
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.core.commands.Count

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

trait QuizDao extends MongoDao {
  
  import com.danielasfregola.quiz.management.model.persistence.QuizEntity._
  import com.danielasfregola.quiz.management.model.api.QuizProtocol._
  
  val collection = db[BSONCollection]("quizzes")

  def save(quizEntity: QuizEntity) = collection.save(quizEntity)
    .map(_ => QuizCreated(quizEntity.id.stringify))
  
  def findById(id: String) =
    collection.find(queryById(id)).one[QuizEntity]
  
  def findOne = {
    val futureCount = db.command(Count(collection.name))
    futureCount.flatMap { count =>
      val skip = Random.nextInt(count)
      collection.find(emptyQuery).options(QueryOpts(skipN = skip)).one[QuizEntity]
    }
  }
  
  def deleteById(id: String) = collection.remove(queryById(id)).map(_ => QuizDeleted)

  private def queryById(id: String) = BSONDocument("_id" -> BSONObjectID(id))

  private def emptyQuery = BSONDocument()
}
