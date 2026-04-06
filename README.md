# HMCTS Backend Demo

This is the backend for the HMCTS case management system.

# About
The system allows caseworkers to efficiently create, track, and manage their tasks.

# Features
- RESTful API with full CRUD operations for case management
- Create tasks with title, description, status, and due date
- View tasks information
- Edit existing tasks
- Delete tasks


# Technology Stack
- Java 21 - Latest LTS Java version
- Spring Boot 3.5.5 - Application framework
- Spring Data JPA - ORM and database interactions
- Spring Boot Validation - Input validation
- PostgreSQL Database - Database for development
- Lombok - Boilerplate code reduction
- JUnit 5 - Testing framework
- Mockito - Mocking framework for tests
- OpenAPI 3 - API documentation
- MockMVC - Test controllers

# Getting Started
## Prerequisites
- Java 21 or higher
- PostgresSQL 18 or higher
- Gradle 8.x (included via wrapper)


## Running the Application
```Bash
# Clone the repository
git clone https://github.com/arzero00/hmcts-demo-backend.git
cd hmcts-demo-backend

# Build the application
./gradlew.bat clean build

# Build a docker image
docker build . -t test-backend:latest

# Build docker compose for Springboot and PostgresSQL
docker-compose up -d
```
The application will now be running on http://localhost:4000

# API Documentation
For info on the API spec, please navigate to a web browser and check out the swagger ui here: http://localhost:4000/swagger-ui/index.html#/

## Endpoints
```
//Get all tasks
GET /api/v1/tasks

//Get a specific task
GET /api/v1/tasks/{id}

//Create a new task
POST /api/v1/tasks

//Update a task
PUT /api/v1/tasks/{id}

//Update a task's status
PATCH /api/v1/tasks/{id}/status

//Delete a task
DELETE /api/v1/tasks/{id}
```

# Error Handling
- Validation Errors (400 Bad Request)
- 404 Not Found responses
- Server errors with user-friendly messages


# Testing
```Bash
./gradlew.bat functional
./gradlew.bat integration
./gradlew.bat test
./gradlew.bat smoke
```
Test Coverage:

- ✅ Controller layer tests with MockMVC
- ✅ Integration tests for full flow: Controller -> Service -> Repository -> Database
- ✅ Exception handling tests
- ✅ Validation tests

# Implementation Details
The backend follows a layered architecture:
- Controllers: Handle HTTP requests and responses
- Services: Implement business logic
- Repositories: Interact with the database
- Models: Define the data structure
