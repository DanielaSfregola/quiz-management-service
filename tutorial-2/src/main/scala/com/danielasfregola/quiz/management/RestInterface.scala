package com.danielasfregola.quiz.management

import akka.actor._
import akka.pattern.pipe
import akka.util.Timeout
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.routing._

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global

class RestInterface extends HttpServiceActor
  with RestApi {

  def receive = runRoute(routes)
}

trait RestApi extends HttpService with ActorLogging { actor: Actor =>
  
  import model.api.QuizProtocol._
  import model.api.QuestionProtocol._

  implicit val timeout = Timeout(10 seconds)
  
  val quizManager = new QuizManager
  val questionManager = new QuestionManager
  
  def routes: Route =
    
    pathPrefix("quizzes") {
      pathEnd {
        post {
          entity(as[Quiz]) { quiz => requestContext =>
            val responder = createResponder(requestContext)
            quizManager.createQuiz(quiz).pipeTo(responder)
          }
        }
      } ~
      path(Segment) { id =>
        delete { requestContext =>
          val responder = createResponder(requestContext)
          quizManager.deleteQuizEntity(id).pipeTo(responder)
        }
      }
    } ~
    pathPrefix("questions") {
      pathEnd {
        get { requestContext =>
          val responder = createResponder(requestContext)
          questionManager.getQuestion().pipeTo(responder)
        }
      } ~
      path(Segment) { id =>
        get { requestContext =>
          val responder = createResponder(requestContext)
          questionManager.getQuestion(Some(id)).pipeTo(responder)
        } ~
        put {
          entity(as[Answer]) { answer => requestContext =>
            val responder = createResponder(requestContext)
            questionManager.answerQuestion(id, answer).pipeTo(responder)
          }
        }
      }
    }
  
  private def createResponder(requestContext: RequestContext) =
    context.actorOf(Props(new Responder(requestContext)))
}

class Responder(requestContext:RequestContext) extends Actor with ActorLogging {
  import model.api.QuizProtocol._
  import model.api.QuestionProtocol._

  def receive = {

    case QuizCreated(id) =>
      requestContext.complete(StatusCodes.Created, id)
      killYourself

    case QuizDeleted =>
      requestContext.complete(StatusCodes.OK)
      killYourself

    case question: Question =>
      requestContext.complete(StatusCodes.OK, question)
      killYourself

    case QuestionNotFound =>
      requestContext.complete(StatusCodes.NotFound)
      killYourself

    case CorrectAnswer =>
      requestContext.complete(StatusCodes.OK)
      killYourself

    case WrongAnswer =>
      requestContext.complete(StatusCodes.NotFound)
      killYourself
  }

  private def killYourself = self ! PoisonPill
  
}
