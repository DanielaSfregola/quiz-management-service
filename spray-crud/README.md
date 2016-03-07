# Quiz Management Service
Tutorial on how to build a REST CRUD application with Spray

Article: <a href="http://danielasfregola.com/2015/11/23/how-to-build-a-scala-rest-crud-application-with-spray/" target="_blank">How to build a Scala REST CRUD application with Spray</a>

## How to run the service
Clone the repository:
```
> git clone https://github.com/DanielaSfregola/quiz-management-service.git
```

Get to the `spray-crud` folder:
```
> cd spray-crud
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
< Server: Quiz Management Service REST API
< Date: Sat, 21 Nov 2015 11:37:11 GMT
< Location: http://localhost:5000/questions/test
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
< Server: Quiz Management Service REST API
< Date: Sat, 21 Nov 2015 11:53:34 GMT
< Content-Length: 0
<
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
< Server: Quiz Management Service REST API
< Date: Sat, 21 Nov 2015 12:23:34 GMT
< Content-Type: application/json; charset=UTF-8
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
< Server: Quiz Management Service REST API
< Date: Sat, 21 Nov 2015 12:25:43 GMT
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
< Server: Quiz Management Service REST API
< Date: Sat, 21 Nov 2015 12:44:03 GMT
< Content-Type: application/json; charset=UTF-8
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
< Server: Quiz Management Service REST API
< Date: Sat, 21 Nov 2015 12:46:15 GMT
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
< HTTP/1.1 200 OK
< Server: Quiz Management Service REST API
< Date: Sat, 21 Nov 2015 12:58:30 GMT
< Content-Type: application/json; charset=UTF-8
< Content-Length: 2
<
* Connection #0 to host localhost left intact
```
