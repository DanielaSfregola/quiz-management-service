package com.danielasfregola.quiz.management.model.api

import com.danielasfregola.quiz.management.model.persistence.QuizEntity

object QuestionProtocol {

  import spray.json._

  case class Question(id: String, question: String)

  case class Answer(answer: String)
  
  /* messages */
  case object QuestionNotFound

  case object CorrectAnswer

  case object WrongAnswer
  
  
  /* json (un)marshalling */

  object Question extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(Question.apply)
  }

  object Answer extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(Answer.apply)
  }
  
  /* implicit conversions */
  
  implicit def toQuestion(quizEntity: QuizEntity): Question = Question(id = quizEntity.id.stringify, question = quizEntity.question)

  implicit def toAnswer(quizEntity: QuizEntity): Answer = Answer(answer = quizEntity.correctAnswer)
}
