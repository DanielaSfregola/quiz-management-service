package com.danielasfregola.quiz.management

import com.danielasfregola.quiz.management.dao.QuizDao
import com.danielasfregola.quiz.management.model.api.QuestionProtocol._
import com.danielasfregola.quiz.management.model.persistence.QuizEntity

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class QuestionManager extends QuizDao {

  def getQuestion(maybeId: Option[String] = None) = {

    def extractQuestion(maybeQuiz: Option[QuizEntity]) = maybeQuiz match {
      case Some(quizEntity) => toQuestion(quizEntity)
      case _ => QuestionNotFound
    }
    tryGetQuiz(maybeId).map(extractQuestion)
  }

  def answerQuestion(id: String, proposedAnswer: Answer) = {
    
    def isAnswerCorrect(maybeQuiz: Option[QuizEntity]) = maybeQuiz match {
      case Some(q) if (q.correctAnswer == proposedAnswer.answer) => CorrectAnswer
      case _ => WrongAnswer
    }
    
    tryGetQuiz(Some(id)).map(isAnswerCorrect)
  }

  private def tryGetQuiz(maybeId: Option[String]): Future[Option[QuizEntity]] = maybeId match {
    case Some(id) => findById(id)
    case _ => findOne
  }
  
}
