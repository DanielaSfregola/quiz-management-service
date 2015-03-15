package com.danielasfregola.quiz.management.model.api

object QuizProtocol {

  import spray.json._

  case class Quiz(question: String, correctAnswer: String)
  
  /* messages */

  case class QuizCreated(id: String)

  case object QuizDeleted
  
  /* json (un)marshalling */
  
  object Quiz extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(Quiz.apply)
  }

}
