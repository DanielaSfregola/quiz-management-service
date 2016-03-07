package com.danielasfregola.quiz.management

import com.danielasfregola.quiz.management.resources.QuestionResource
import com.danielasfregola.quiz.management.services.QuestionService
import spray.routing._

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

class RestInterface(implicit val executionContext: ExecutionContext) extends HttpServiceActor with Resources {

  def receive = runRoute(routes)

  val questionService = new QuestionService

  val routes: Route = questionRoutes

}

trait Resources extends QuestionResource
