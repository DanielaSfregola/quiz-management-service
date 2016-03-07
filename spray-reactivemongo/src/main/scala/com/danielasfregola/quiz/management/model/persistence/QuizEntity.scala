package com.danielasfregola.quiz.management.model.persistence

import com.danielasfregola.quiz.management.model.api.QuizProtocol.Quiz
import reactivemongo.bson.{BSONDocumentWriter, BSONDocument, BSONDocumentReader, BSONObjectID}

case class QuizEntity(id: BSONObjectID = BSONObjectID.generate,
                      question: String, 
                      correctAnswer: String)

object QuizEntity {

  implicit def toQuizEntity(quiz: Quiz) = QuizEntity(question = quiz.question, correctAnswer = quiz.correctAnswer)

  implicit object QuizEntityBSONReader extends BSONDocumentReader[QuizEntity] {
    
    def read(doc: BSONDocument): QuizEntity = 
      QuizEntity(
        id = doc.getAs[BSONObjectID]("_id").get,
        question = doc.getAs[String]("question").get,
        correctAnswer = doc.getAs[String]("answer").get
      )
  }
  
  implicit object QuizEntityBSONWriter extends BSONDocumentWriter[QuizEntity] {
    def write(quizEntity: QuizEntity): BSONDocument =
      BSONDocument(
        "_id" -> quizEntity.id,
        "question" -> quizEntity.question,
        "answer" -> quizEntity.correctAnswer
      )
  }
}


