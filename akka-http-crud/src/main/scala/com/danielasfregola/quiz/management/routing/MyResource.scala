package com.danielasfregola.quiz.management.routing

import akka.http.scaladsl.marshalling.{ToResponseMarshallable, ToResponseMarshaller}
import akka.http.scaladsl.model.StatusCode

import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.server.{Directives, Route}
import com.danielasfregola.quiz.management.serializers.JsonSupport

trait MyResource extends Directives with JsonSupport {

  implicit def executionContext: ExecutionContext

  def completeWithLocationHeader[T](resourceId: Future[Option[T]], ifDefinedStatus: StatusCode, ifEmptyStatus: StatusCode): Route =
    onSuccess(resourceId) {
      case Some(t) => completeWithLocationHeader(ifDefinedStatus, t)
      case None => complete(ifEmptyStatus)
    }

  def completeWithLocationHeader[T](status: StatusCode, resourceId: T): Route =
    extractRequestContext { requestContext =>
      val request = requestContext.request
      val location = request.uri.copy(path = request.uri.path / resourceId.toString)
      respondWithHeader(Location(location)) {
        complete(status)
      }
    }

  def complete[T: ToResponseMarshaller](resource: Future[Option[T]]): Route =
    onSuccess(resource) {
      case Some(t) => complete(ToResponseMarshallable(t))
      case None => complete(StatusCode.int2StatusCode(404))
    }

  def complete(resource: Future[Unit]): Route = onSuccess(resource) { complete(StatusCode.int2StatusCode(204)) }

}
