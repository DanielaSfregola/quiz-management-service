package com.danielasfregola.quiz.management.serializers

import com.danielasfregola.quiz.management.entities.{Question, QuestionUpdate}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, Reads}

trait JsonSupport extends PlayJsonSupport {
  implicit val questionJsonFormat = Json.format[Question]
  implicit val questionReads: Reads[Question] = (
    (JsPath \ "id").read[String] and
      (JsPath \ "title").read[String] and
      (JsPath \ "text").read[String]
    )(Question.apply _)

  implicit val questionUpdateJsonFormat = Json.format[QuestionUpdate]
  implicit val questionUpdateReads: Reads[QuestionUpdate] = (
    (JsPath \ "title").readNullable[String] and
      (JsPath \ "text").readNullable[String]
    )(QuestionUpdate.apply _)
}
