package com.danielasfregola.quiz.management

import com.danielasfregola.quiz.management.dao.QuizDao
import com.danielasfregola.quiz.management.model.persistence.QuizEntity

class QuizManager extends QuizDao {

  def createQuiz(quizEntity: QuizEntity) = save(quizEntity)

  def deleteQuizEntity(id: String) = deleteById(id)
  
}
