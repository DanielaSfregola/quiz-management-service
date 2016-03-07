package com.danielasfregola.quiz.management

import scala.concurrent.ExecutionContext

import akka.http.scaladsl.server.Route

import com.danielasfregola.quiz.management.resources.QuestionResource
import com.danielasfregola.quiz.management.services.QuestionService

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  lazy val questionService = new QuestionService

  val routes: Route = questionRoutes

}

trait Resources extends QuestionResource

