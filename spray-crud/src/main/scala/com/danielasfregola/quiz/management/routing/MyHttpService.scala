package com.danielasfregola.quiz.management.routing

import com.danielasfregola.quiz.management.serializers.JsonSupport
import spray.http.HttpHeaders
import spray.routing._

import scala.concurrent.{ExecutionContext, Future}

trait MyHttpService extends HttpService with JsonSupport {

  implicit val executionContext: ExecutionContext

  def completeWithLocationHeader[T](resourceId: Future[Option[T]], ifDefinedStatus: Int, ifEmptyStatus: Int): Route =
    onSuccess(resourceId) { maybeT =>
      maybeT match {
        case Some(t) => completeWithLocationHeader(ifDefinedStatus, t)
        case None => complete(ifEmptyStatus, None)
      }
    }

  def completeWithLocationHeader[T](status: Int, resourceId: T): Route =
    requestInstance { request =>
      val location = request.uri.copy(path = request.uri.path / resourceId.toString)
      respondWithHeader(HttpHeaders.Location(location)) {
        complete(status, None)
      }
    }

}
