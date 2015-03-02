package com.danielasfregola.quiz.management

import akka.actor._
import akka.util.Timeout
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.routing._

import scala.concurrent.duration._
import scala.language.postfixOps

class RestInterface extends HttpServiceActor
  with RestApi {

  def receive = runRoute(routes)
}

trait RestApi extends HttpService with ActorLogging { actor: Actor =>
  import com.danielasfregola.quiz.management.QuizProtocol._

  implicit val timeout = Timeout(10 seconds)

  var quizzes = Vector[Quiz]()

  def routes: Route =
    
    pathPrefix("quizzes") {
      pathEnd {
        post {
          entity(as[Quiz]) { quiz => requestContext =>
            val responder = createResponder(requestContext)
            createQuiz(quiz) match {
              case true => responder ! QuizCreated
              case _ => responder ! QuizAlreadyExists
            }
          }
        }
      } ~
      path(Segment) { id =>
        delete { requestContext =>
          val responder = createResponder(requestContext)
          deleteQuiz(id)
          responder ! QuizDeleted
        }
      }
    } ~
    pathPrefix("questions") {
      pathEnd {
        get { requestContext =>
          val responder = createResponder(requestContext)
          getRandomQuestion.map(responder ! _)
            .getOrElse(responder ! QuestionNotFound)
        }
      } ~
      path(Segment) { id =>
        get { requestContext =>
          val responder = createResponder(requestContext)
          getQuestion(id).map(responder ! _)
            .getOrElse(responder ! QuestionNotFound)
        } ~
        put {
          entity(as[Answer]) { answer => requestContext =>
            val responder = createResponder(requestContext)
            isAnswerCorrect(id, answer) match {
              case true => responder ! CorrectAnswer
              case _ => responder ! WrongAnswer
            }
          }
        }
      }
    }

  private def createResponder(requestContext:RequestContext) = {
    context.actorOf(Props(new Responder(requestContext)))
  }

  private def createQuiz(quiz: Quiz): Boolean = {
    val doesNotExist = !quizzes.exists(_.id == quiz.id)
    if (doesNotExist) quizzes = quizzes :+ quiz
    doesNotExist
  }
  
  private def deleteQuiz(id: String): Unit = {
    quizzes = quizzes.filterNot(_.id == id)
  }
  
  private def getRandomQuestion: Option[Question] = {
    !quizzes.isEmpty match {
      case true =>
        import scala.util.Random
        val idx = (new Random).nextInt(quizzes.size)
        Some(quizzes(idx))
      case _ => None
    }
  }
  
  private def getQuestion(id: String): Option[Question] = {
    getQuiz(id).map(toQuestion)
  }
  
  private def getQuiz(id: String): Option[Quiz] = {
    quizzes.find(_.id == id)
  }
  
  private def isAnswerCorrect(id: String, proposedAnswer: Answer): Boolean = {
    getQuiz(id).exists(_.correctAnswer == proposedAnswer.answer)
  }
}

class Responder(requestContext:RequestContext) extends Actor with ActorLogging {
  import com.danielasfregola.quiz.management.QuizProtocol._
  
  def receive = {

    case QuizCreated =>
      requestContext.complete(StatusCodes.Created)
      killYourself

    case QuizDeleted =>
      requestContext.complete(StatusCodes.OK)
      killYourself

    case QuizAlreadyExists =>
      requestContext.complete(StatusCodes.Conflict)
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
