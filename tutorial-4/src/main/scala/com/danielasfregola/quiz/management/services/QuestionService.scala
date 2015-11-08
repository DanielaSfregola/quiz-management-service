package com.danielasfregola.quiz.management.services

import scala.concurrent.{ExecutionContext, Future}

case class Question(id: String, title: String, text: String)

case class QuestionUpdate(title: Option[String], text: Option[String])

class QuestionService(implicit executionContext: ExecutionContext) {

  var questions = Vector.empty[Question]

  def createQuestion(question: Question): Future[Option[String]] = Future {
    questions.find(_.id == question.id) match {
      case Some(q) => None // conflict! id is already taken
      case None =>
        questions = questions :+ question
        Some(question.id)
    }
  }

  def getQuestion(id: String): Future[Option[Question]] = Future {
    questions.find(_.id == id)
  }

  def updateQuestion(id: String, update: QuestionUpdate): Future[Option[String]] = {

    def updateEntity(question: Question): Question = {
      val title = update.title.getOrElse(question.title)
      val text = update.text.getOrElse(question.text)
      Question(id, title, text)
    }

    getQuestion(id).flatMap { maybeQuestion =>
      maybeQuestion match {
        case None => Future { None } // No question found
        case Some(question) =>
          val updatedQuestion = updateEntity(question)
          deleteQuestion(id).flatMap(_ => createQuestion(updatedQuestion))
      }
    }
  }

  def deleteQuestion(id: String): Future[Unit] = Future {
    questions.filterNot(_.id == id)
  }


}

