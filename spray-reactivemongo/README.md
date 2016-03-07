# Quiz Management Service
Tutorial on how to build a REST api with Spray, Akka and ReactiveMongo

Article: <a href="http://danielasfregola.com/2015/03/16/how-to-integrate-reactivemongo-in-your-akka-spray-application/" target="_blank">How to Integrate ReactiveMongo in your Akka Spray Application</a>

## How to run the service
Clone the repository:
```
> git clone https://github.com/DanielaSfregola/quiz-management-service.git
```

Get to the `spray-reactivemongo` folder:
```
> cd spray-reactivemongo
```

Run the service:
```
> sbt run
```

The service runs on port 5000 by default.

## Usage
Quiz entity:
```
case class Quiz(id: String, question: String, correctAnswer: String)
```
Question entity:
```
case class Question(id: String, question: String)
```
Answer entity:
```
case class Answer(answer: String)
```

### Create a quiz
```
curl -v -H "Content-Type: application/json" \
     -X POST http://localhost:5000/quizzes \
     -d '{"id": "test", "question": "is scala cool?", "correctAnswer": "YES!"}'
```

### Delete a quiz
```
curl -v -X DELETE http://localhost:5000/quizzes/test
```

### Get a random question
```
curl -v http://localhost:5000/questions
```

### Get a question by id
```
curl -v http://localhost:5000/questions/test
```

### Answer a question
```
curl -v -H "Content-Type: application/json" \
     -X PUT http://localhost:5000/questions/test \
     -d '{ "answer": "YES!" }'
```
