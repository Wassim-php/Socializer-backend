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

# JavaTech Backend

This repository contains the backend for the JavaTech social application — a Spring Boot REST API that provides user authentication (JWT), posts, comments, tags, and file upload support.

---

## Key Features
- JWT-based authentication: register, login, token validate and logout (token revocation).
- CRUD for Posts and Comments, plus like / unlike operations.
- Add comments to posts and fetch comments by post id.
- File upload endpoint (uploads stored under `uploads/`).
- Role-aware endpoints (some actions require authentication and/or ownership).

---

## Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Lombok (compile-time)

---

## Quickstart (development)
1. Install Java 17 and PostgreSQL.
2. Clone the repository:

```powershell
git clone https://github.com/Wassim-php/Socializer-backend.git
cd "d:/Java Tech"
```

3. Configure the database and JWT in `src/main/resources/application.properties` or via environment variables. Default properties in the project:

```properties
spring.application.name=javatech
server.port=7007

# Example PostgreSQL connection (update for your env)
spring.datasource.url=jdbc:postgresql://localhost:5432/csis_231_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=<your-base64-256bit-secret>
jwt.expiration=86400000
```


4. Build and run the app:

```powershell
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

The server will start on `http://localhost:7007` by default.

---

## API Overview
Base path: `/api`

Authentication
- `POST /api/auth/register` — Register a new user (body: `RegisterRequest`).
- `POST /api/auth/login` — Login and receive an `AuthResponse` with JWT (body: `LoginRequest`).
- `GET /api/auth/validate` — Validate a token (send `Authorization: Bearer <token>` header).
- `POST /api/auth/logout` — Logout / revoke token (send `Authorization: Bearer <token>` header).

Posts
- `POST /api/posts/create` — Create a post (authenticated).
- `GET /api/posts/all` — Get all posts.
- `GET /api/posts/{id}` — Get post by id.
- `PUT /api/posts/update/{id}` — Update a post (authenticated, ownership rules may apply).
- `DELETE /api/posts/delete/{id}` — Delete a post.
- `POST /api/posts/likePost/{id}` — Like a post.
- `POST /api/posts/unlikePost/{id}` — Unlike a post.
- `POST /api/posts/{id}/addComment` — Add a comment to a post.

Comments
- `POST /api/comments/create` — Create a comment.
- `GET /api/comments/all` — Get all comments.
- `GET /api/comments/{id}` — Get comment by id.
- `PUT /api/comments/update/{id}` — Update comment.
- `DELETE /api/comments/delete/{id}` — Delete comment.
- `GET /api/comments/all/{postId}` — Get comments for a post.

Users
- `POST /api/users/create` — Create a user (body: `UserCreateDTO`).
- `GET /api/users/all` — Get all users.
- `GET /api/users/{id}` — Get user by id.
- `PUT /api/users/update/{id}` — Update a user (body: `UserUpdateDTO`).
- `DELETE /api/users/delete/{id}` — Delete a user.

Tags
- `POST /api/tags/create` — Create a tag (body: `TagCreateDTO`).
- `GET /api/tags/all` — Get all tags.
- `GET /api/tags/{id}` — Get tag by id.
- `PUT /api/tags/update/{id}` — Update a tag (body: `TagUpdateDTO`).
- `DELETE /api/tags/delete/{id}` — Delete a tag.

Uploads
- `POST /api/uploads` — Upload a file (multipart form field: `file`). Returns the stored path on success.
- `GET /api/uploads/{filename}` — Retrieve a previously uploaded file.

Authentication note: the application secures most endpoints — `/api/auth/**` is public while `/api/posts/**`, `/api/users/**`, and `/api/tags/**` require authentication as configured in `SecurityConfig`.

Security
- The app uses JWT tokens. The `SecurityConfig` permits `/api/auth/**` to be public; most other API paths require authentication.

---

## Configuration & Environment
- Recommended: don't commit secrets. Use environment variables or a secrets manager for `jwt.secret` and database credentials.
- Example environment variables mapping:

```powershell
setx SPRING_DATASOURCE_URL "jdbc:postgresql://localhost:5432/csis_231_db"
setx SPRING_DATASOURCE_USERNAME "postgres"
setx SPRING_DATASOURCE_PASSWORD "postgres"
setx JWT_SECRET "<base64-256-secret>"
```

---

## Project structure (important packages)
- `com.example.javatech.auth` — authentication, JWT util, login/register DTOs, token revocation.
- `com.example.javatech.post` — post entity, DTOs, controller and service.
- `com.example.javatech.comment` — comment entity, DTOs, controller and service.
- `com.example.javatech.user` — user entity and user-related endpoints.
- `com.example.javatech.tag` — tag entity and tag endpoints.
- `com.example.javatech.security` — Spring Security configuration and JWT filter.

---

## Tests
- Unit and integration tests use Spring Boot test support. Run them with:

```powershell
mvnw.cmd test
```

---



