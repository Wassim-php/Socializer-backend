# JavaTech Backend

This is the backend for the JavaTech social media application. It provides REST APIs for user authentication, posts, comments, likes, and role-based actions.

---

## Features
- User registration and login with **JWT authentication**
- Logout endpoint with token invalidation
- Create, read, update, delete **posts**
- Like and comment on posts
- Role-based permissions for deleting posts and comments
- Fetch comments by post ID
- Fully RESTful API with JSON responses

---

## Tech Stack
- Java 17+
- Spring Boot 3+
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven

---

## Setup Instructions
1. Clone the repo:
```bash
git clone https://github.com/yourusername/Sociolizer-backend.git
```

2. Configure database in src/main/resources/application.properties:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/javatech
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```
3. Run the backend:
```bash
mvn spring-boot:run
```
---


### API ENDPOINTS
| Endpoint                  | Method | Description                   |
| ------------------------- | ------ | ----------------------------- |
| `/api/auth/register`      | POST   | Register a new user           |
| `/api/auth/login`         | POST   | Login user and return JWT     |
| `/api/auth/logout`        | POST   | Logout user (invalidate JWT)  |
| `/api/posts`              | GET    | Fetch all posts               |
| `/api/posts`              | POST   | Create a new post             |
| `/api/posts/{id}`         | GET    | Fetch a single post           |
| `/api/posts/{id}`         | DELETE | Delete a post (only owner)    |
| `/api/posts/{id}/like`    | POST   | Like/unlike a post            |
| `/api/comments`           | GET    | Fetch all comments            |
| `/api/comments/post/{id}` | GET    | Fetch comments by post ID     |
| `/api/comments`           | POST   | Create a comment              |
| `/api/comments/{id}`      | DELETE | Delete a comment (only owner) |


