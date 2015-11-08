package com.danielasfregola.quiz.management.resources

import com.danielasfregola.quiz.management.serializers.JsonSupport
import com.danielasfregola.quiz.management.services.{Question, QuestionService, QuestionUpdate}
import spray.routing._

import scala.concurrent.ExecutionContext

trait QuestionResource extends HttpService with JsonSupport {

  implicit val executionContext: ExecutionContext

  val questionService: QuestionService

  def questionRoutes: Route = pathPrefix("questions") {
    pathEnd {
      post {
        entity(as[Question]) { question =>
          questionService.createQuestion(question)
          ???
        }
      }
    } ~
    path(Segment) { id =>
      get {
        complete(questionService.getQuestion(id))
      } ~
      put {
        entity(as[QuestionUpdate]) { update =>
          questionService.updateQuestion(id, update)
          ???
        }
      }
      delete {
        complete(questionService.deleteQuestion(id))
      }
    }
  }

}
