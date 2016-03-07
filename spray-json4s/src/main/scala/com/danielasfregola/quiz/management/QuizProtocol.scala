package com.danielasfregola.quiz.management

import com.danielasfregola.quiz.management.serializers.JsonSupport

object QuizProtocol extends JsonSupport {

  case class Quiz(id: String, question: String, correctAnswer: String)
  
  case object QuizCreated
  
  case object QuizAlreadyExists
  
  case object QuizDeleted
  
  case class Question(id: String, question: String)
  
  case object QuestionNotFound
  
  case class Answer(answer: String)
  
  case object CorrectAnswer
  
  case object WrongAnswer

  implicit def toQuestion(quiz: Quiz): Question = Question(id = quiz.id, question = quiz.question)

  implicit def toAnswer(quiz: Quiz): Answer = Answer(answer = quiz.correctAnswer)
}
