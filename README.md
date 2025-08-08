# 📚 Novel Management System (Spring Boot)

A backend system to manage novels using **Spring Boot**, **JPA**, **MapStruct**, and **DTO architecture**. It follows a clean layered design with proper separation of concerns and integrates logging and exception handling for robustness.

---

## 🚀 Features

- ✅ Add novels with **unique names and links**
- 🔄 Prevent duplicate entries via **service-layer validation**
- 📝 Store novel **details/descriptions** in a separate entity (`NovelDetails`)
- 🔁 MapStruct-based **DTO ↔ Entity** conversion
- 🧠 Global exception handling
- 🧪 Swagger/OpenAPI UI for API testing
- 📃 Meaningful logging with SLF4J
- 🔐 JPA handles database schema generation

---

## 🧩 Tech Stack

- Java 17+
- Spring Boot 3.5.4
- Spring Data JPA (Hibernate)
- MapStruct
- Lombok
- MySQL
- Swagger/OpenAPI
- SLF4J + Logback

---

## 🗂️ Project Structure

```
com.novel.web
├── configuration
│   └── DbConfig.java
├── controller
│   └── NovelController.java           // REST endpoints
├── domain
│   ├── Novel.java                     // Main entity
│   └── NovelDetails.java              // Details entity with OneToOne relation
├── dto
│   └── request
│       ├── NovelRequestDTO.java
│       └── NovelDetailsRequestDTO.java
├── exception
│   └── NovelException.java            // Custom exceptions
├── mapper
│   └── NovelRequestMapper.java        // MapStruct mapper
├── repositories
│   └── NovelRepository.java
├── service
│   ├── NovelService.java              // Service interface
│   └── impl
│       └── NovelServiceImpl.java      // Business logic implementation
├── WebApplication.java                // Spring Boot main class
└── resources
    └── static                         // Place favicon/static files here
```

---

## 📑 API Endpoints

> Accessible via **Swagger UI** at `http://localhost:8080/swagger-ui/index.html`

| Method | Endpoint        | Description                    |
| ------ | --------------- | ------------------------------ |
| GET    | `/home` or `/`  | Health check / welcome message |
| POST   | `/add`          | Add a new novel                |
| GET    | `/name?name=`   | Search novels by name          |
| GET    | `/genre?genre=` | Search novels by genre         |

---

## 🪵 Logging

- Enabled across **controller**, **service**, and **exception** layers
- Uses `@Slf4j` (Lombok) and SLF4J backend
- Log levels used:
  - `info` – normal operations
  - `warn` – duplicate or suspicious data
  - `error` – unexpected exceptions

---

## ❗ Exception Handling

- Custom `NovelException.java` for meaningful error propagation
- Global exception handling with `@ControllerAdvice` (if added)
- Returns appropriate HTTP status codes like:
  - `409 CONFLICT` for duplicates
  - `400 BAD_REQUEST` for validation failures
  - `500 INTERNAL_SERVER_ERROR` for server-side errors
