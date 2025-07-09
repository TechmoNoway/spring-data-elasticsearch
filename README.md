# Spring Data Elasticsearch Project

## Overview
This project demonstrates the use of **Spring Data Elasticsearch** to perform CRUD operations on Elasticsearch indices. It includes a simple implementation for managing `Person` documents in an Elasticsearch index.

## Features
- Save `Person` documents to Elasticsearch.
- Retrieve `Person` documents by ID.
- Elasticsearch index configuration using custom settings.

## Technologies Used
- **Java**: Programming language.
- **Spring Boot**: Framework for building the application.
- **Spring Data Elasticsearch**: Integration with Elasticsearch.
- **Gradle**: Build tool.

## Project Structure
- `src/main/java/com/trikynguci/springdataelasticsearch/document/Person.java`: Defines the `Person` document structure.
- `src/main/java/com/trikynguci/springdataelasticsearch/repository/PersonRepository.java`: Repository interface for Elasticsearch operations.
- `src/main/java/com/trikynguci/springdataelasticsearch/service/PersonService.java`: Service layer for business logic.
- `src/main/java/com/trikynguci/springdataelasticsearch/controller/PersonController.java`: REST API controller for handling HTTP requests.

## Endpoints
### Save a Person
**POST** `/api/person`  
Request Body:
```json
{
  "id": "1",
  "name": "John Doe"
}
```

### Get a Person by ID
**GET** `/api/person/{id}`  
Response:
```json
{
  "id": "1",
  "name": "John Doe"
}
```

## Setup Instructions
1. Clone the repository.
2. Ensure Elasticsearch is running locally or remotely.
3. Update the `application.properties` file with your Elasticsearch configuration.
4. Build the project using Gradle:
   ```bash
   gradle build
   ```
5. Run the application:
   ```bash
   gradle bootRun
   ```

## Elasticsearch Configuration
The `Person` index is configured with custom settings located in `static/es-settings.json`. Ensure this file is properly set up before running the application.

## License
This project is licensed under the MIT License.