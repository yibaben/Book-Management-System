# Mobilise Book Management System

## Objective
Development of a simple RESTful web service using Java and Spring Boot for a book management system.

## Features
### CRUD Operations:
- **Create:** To Add a new book to the system.
- **Read:** To Retrieve details of all books or a specific book by ID.
- **Update:** To Modify details of an existing book.
- **Delete:** To Remove a book from the system.

### Search Feature:
- I Provided an endpoint to search books by title, author, ISBN or publicationYear.

### Pagination:
- I Implemented pagination for the "Retrieve all books" feature.

## Technical Specifications
- The application was built using Java 21 and Spring Boot version: 3.2.5 .
- For database access, I use Spring Data JPA and H2 in-memory database for data storage.
- I Include validation checks. For instance, a book should have a valid title, author, and publication year based on the criteria I defined.
- I Implement proper exception handling. For example, when trying to fetch a book by ID that doesn't exist, it should return a Not Found status.
- I Ensure the application is properly structured, separating concerns (controllers, services, repositories, etc.).
- I Ensure the code is well documented and explained with Docstring and comments.

## Test (optional)
- I Added unit tests for the service layer.

## API Responses
- The API responses is in JSON format.

## REST API Endpoints
### Create a new book
- Endpoint: `http://localhost:8000/api/v1/book/add`
- Method: `POST`
- Request Body: `BookRequest` object
```json
{
  "title": "Mobilise 100Plus Success",
  "author": "J.J Bennett",
  "isbn": "ISBN12345",
  "quantity": 10,
  "publicationYear": 2020
}
```
- Response: `BookResponse` object
```json
{
    "message": "successful",
    "status": "OK",
    "dateTime": "2024-05-01T16:20:53.1802",
    "data": {
        "id": 1,
        "title": "Mobilise 100Plus Success",
        "author": "J.J Bennett",
        "isbn": "ISBN12345",
        "quantity": 10,
        "publicationYear": "2020"
    }
}
```
### Get all books with pagination
- Endpoint: `http://localhost:8000/api/v1/book/get/all?pageNo=0&pageSize=5`
- Method: `GET`
- Query Parameters:
    - `pageNo` (optional): Page number (default: 0)
    - `pageSize` (optional): Number of books per page (default: 10)
- Response: `PaginatedBookResponse` object
```json
{
    "message": "successful",
    "status": "OK",
    "dateTime": "2024-05-01T17:05:08.347925",
    "data": {
        "contents": [
            {
                "id": 1,
                "title": "Mobilise 100Plus Success",
                "author": "J.J Bennett",
                "isbn": "ISBN12345",
                "quantity": 10,
                "publicationYear": "2020"
            },
            {
                "id": 2,
                "title": "Mobilise HERO Nigeria",
                "author": "J.J Bennett",
                "isbn": "ISBN12345",
                "quantity": 10,
                "publicationYear": "2012"
            },
            {
                "id": 3,
                "title": "Mobilise eSIM Nigeria",
                "author": "J.J Bennett",
                "isbn": "ISBN12345",
                "quantity": 10,
                "publicationYear": "2020"
            }
        ],
        "pageElementCount": 3,
        "pageSize": 5
    }
}
```

### Get a book by ID
- Endpoint: `http://localhost:8000/api/v1/book/get/by/id/1`
- Method: `GET`
- Path Variable: `id` (ID of the book)
- Response: `BookResponse` object
```json
{
    "message": "successful",
    "status": "OK",
    "dateTime": "2024-05-01T17:07:11.690542",
    "data": {
        "id": 1,
        "title": "Mobilise 100Plus Success",
        "author": "J.J Bennett",
        "isbn": "ISBN12345",
        "quantity": 10,
        "publicationYear": "2020"
    }
}
```

### Search books by title or author or ISBN or publicationYear
- Endpoint: `http://localhost:8000/api/v1/book/search/by/searchText/HERO`
- Method: `GET`
- Path Variable: `searchText` (Text to search for)
- Response: List of `BookResponse` objects
```json
{
    "message": "successful",
    "status": "OK",
    "dateTime": "2024-05-01T17:09:42.656771",
    "data": [
        {
            "id": 2,
            "title": "Mobilise HERO Nigeria",
            "author": "J.J Bennett",
            "isbn": "ISBN12345",
            "quantity": 10,
            "publicationYear": "2012"
        }
    ]
}
```

### Update book details by ID
- Endpoint: `http://localhost:8000/api/v1/book/update/by/id/2`
- Method: `PUT`
- Path Variable: `id` (ID of the book)
- Request Body: `BookRequest` object
```json
{
  "author": "J.J Wenifiri Mira",
  "isbn": "ISBN00127G0",
  "quantity": 100,
  "publicationYear": 2023
}
```
- Response: `BookResponse` object
```json
{
    "message": "successful",
    "status": "OK",
    "dateTime": "2024-05-01T17:13:38.503854",
    "data": {
        "id": 2,
        "title": "Mobilise HERO Nigeria",
        "author": "J.J Wenifiri Mira",
        "isbn": "ISBN00127G0",
        "quantity": 100,
        "publicationYear": "2023"
    }
}
```

### Delete a book by ID
- Endpoint: `http://localhost:8000/api/v1/book/delete/by/id/1`
- Method: `DELETE`
- Path Variable: `id` (ID of the book)
- Response: `ApiResponse` object
```json
{
    "message": "successful",
    "status": "OK",
    "dateTime": "2024-05-01T17:10:58.20785",
    "data": {}
}
```

## Cloning and Running the Application
1. Clone the repository or download the zip file to your local machine.
2. Navigate to the project directory.
3. Run the application using the following command:
```
mvn spring-boot:run
```

4. Once the application is running, you can also access the Swagger API documentation at: [Swagger Documentation](http://localhost:8000/api/v1/swagger-ui/index.html#/)
5. You can also access the H2 in-memory database at: [H2 Database](http://localhost:8000/api/v1/h2-console/)
6. You can also test the endpoints using tools like Postman or curl commands.

## Testing the Application
1. For testing the application, you can use tools like Swagger or Postman to send requests to the endpoints.
2. Ensure to test all CRUD operations, search functionality, and pagination.
3. Look out for Exceptions and Error messages in the ApiResponse object.
4. Verify that the responses are as expected based on the provided sample responses in the README.
