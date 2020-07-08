# Mongo Numbers REST API

Project for store numbers in MongoDB via RESTful API

## Tech Stack

* Java 8
* Spring Boot 2.3
* Maven 3
* MongoDB 4.2

## Notes on Init

* To run application, you can use this command:

```
mvn spring-boot:run
```

* Before running the application, you should create a DB called "stored-numbers" on local MongoDB server.

## Endpoints

* Stores number value

```
POST /stored-number

{
  "value": 13
}
```

* Lists stored numbers

```
GET /stored-number?value=13&isOrderAscending=true
```

* Finds minimum number value stored

```
GET /stored-number/minimum
```

* Finds maximum number value stored

```
GET /stored-number/maximum
```

* Deletes by value

```
DELETE /stored-number/{value}
```