# Quiz Management Service
Tutorial on how to build a REST CRUD application with Akka-Http

Article: http://danielasfregola.com/2016/02/07/how-to-build-a-rest-api-with-akka-http/

## How to run the service
Clone the repository:
```
> git clone https://github.com/DanielaSfregola/quiz-management-service.git
```

Get to the `akka-http-crud` folder:
```
> cd akka-http-crud
```

Run the service:
```
> sbt run
```

The service runs on port 5000 by default.

## Usage

Question entity:
```
case class Question(id: String, title: String, text: String)
```

### Create a question
Request:
```
curl -v -H "Content-Type: application/json" \
	 -X POST http://localhost:5000/questions \
	 -d '{"id": "test", "title": "MyTitle", "text":"The text of my question"}'
```
Response if the question has been created:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> POST /questions HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 68
> 
* upload completely sent off: 68 out of 68 bytes
< HTTP/1.1 201 Created
< Location: http://localhost:5000/questions/test
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:16:50 GMT
< Content-Type: application/json
< Content-Length: 0
< 
* Connection #0 to host localhost left intact

```
Response if the question with the specified id already exists:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> POST /questions HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 68
> 
* upload completely sent off: 68 out of 68 bytes
< HTTP/1.1 409 Conflict
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:17:07 GMT
< Content-Type: application/json
< Content-Length: 0
< 
* Connection #0 to host localhost left intact
```


### Get a question
Request:
```
curl -v http://localhost:5000/questions/test
```
Response if the question exists:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> GET /questions/test HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:17:31 GMT
< Content-Type: application/json
< Content-Length: 64
< 
* Connection #0 to host localhost left intact
{"id":"test","title":"MyTitle","text":"The text of my question"}
```
Response if the question does not exist:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> GET /questions/non-existing-question HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> 
< HTTP/1.1 404 Not Found
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:18:40 GMT
< Content-Type: application/json
< Content-Length: 0
< 
* Connection #0 to host localhost left intact
```

### Update a question
Request:
```
curl -v -H "Content-Type: application/json" \
	 -X PUT http://localhost:5000/questions/test \
	 -d '{"text":"Another text"}'
```
Response if the question has been updated:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> PUT /questions/test HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 23
> 
* upload completely sent off: 23 out of 23 bytes
< HTTP/1.1 200 OK
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:19:31 GMT
< Content-Type: application/json
< Content-Length: 53
< 
* Connection #0 to host localhost left intact
{"id":"test","title":"MyTitle","text":"Another text"}
```
Response if the question could not be updated:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> PUT /questions/non-existing-question HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 23
> 
* upload completely sent off: 23 out of 23 bytes
< HTTP/1.1 404 Not Found
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:20:07 GMT
< Content-Type: application/json
< Content-Length: 0
< 
* Connection #0 to host localhost left intact
```

### Delete a question
Request:
```
curl -v -X DELETE http://localhost:5000/questions/test
```
Response:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> DELETE /questions/test HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> 
< HTTP/1.1 204 No Content
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:20:30 GMT
< Content-Type: application/json
< 
* Connection #0 to host localhost left intact
```
