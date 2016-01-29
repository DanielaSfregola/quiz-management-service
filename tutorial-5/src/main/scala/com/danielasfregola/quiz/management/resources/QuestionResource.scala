package com.danielasfregola.quiz.management.resources

import akka.http.scaladsl.server.Route

import com.danielasfregola.quiz.management.entities.{Question, QuestionUpdate}
import com.danielasfregola.quiz.management.routing.MyHttpService
import com.danielasfregola.quiz.management.services.QuestionService

trait QuestionResource extends MyHttpService {

  val questionService: QuestionService

  def questionRoutes: Route = pathPrefix("questions") {
    pathEnd {
      post {
        entity(as[Question]) { question =>
          completeWithLocationHeader(
            resourceId = questionService.createQuestion(question),
            ifDefinedStatus = 201, ifEmptyStatus = 409)
          }
        }
    } ~
    path(Segment) { id =>
      get {
        complete(questionService.getQuestion(id))(asOption)
      } ~
      put {
        entity(as[QuestionUpdate]) { update =>
          complete(questionService.updateQuestion(id, update))(asOption)
        }
      } ~
      delete {
        complete(questionService.deleteQuestion(id))(asEmpty)
      }
    }

  }
}

