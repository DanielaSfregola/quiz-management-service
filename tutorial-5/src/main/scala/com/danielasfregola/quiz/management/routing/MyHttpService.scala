package com.danielasfregola.quiz.management.routing

import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.server.{Directives, Route}

import com.danielasfregola.quiz.management.serializers.JsonSupport

trait MyHttpService extends Directives with JsonSupport {

  implicit def executionContext: ExecutionContext

  def completeWithLocationHeader[T](resourceId: Future[Option[T]], ifDefinedStatus: Int, ifEmptyStatus: Int): Route =
    onSuccess(resourceId) { maybeT =>
      maybeT match {
        case Some(t) => completeWithLocationHeader(ifDefinedStatus, t)
        case None => complete(ifEmptyStatus, None)
      }
    }

  def completeWithLocationHeader[T](status: Int, resourceId: T): Route =
    extractRequestContext { requestContext =>
      val request = requestContext.request
      val location = request.uri.copy(path = request.uri.path / resourceId.toString)
      respondWithHeader(Location(location)) {
        complete(status, None)
      }
    }

  def asOption[T](resource: Option[T]): Route = resource match {
    case Some(x) => complete(200, x)
    case None => complete(404, None)
  }
  
  val asEmpty: Unit => Route = { _ => complete(204, None) }

  def complete[T](resource: Future[T])(f: T => Route): Route = onSuccess(resource)(f)


}
