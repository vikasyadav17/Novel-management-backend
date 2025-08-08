# 📚 Novel Management System (Spring Boot)

A backend system to manage novels using Spring Boot, JPA, MapStruct, and DTO architecture.

---

## 🚀 Features

- Add novels with unique names.
- Prevent duplicates using service-layer checks.
- Store novel descriptions in a separate table.
- DTO to Entity mapping using MapStruct.
- Clean separation between Controller, Service, and Repository layers.
- Global exception handling.
- Logging using SLF4J (Logback).
- Interactive API docs with Swagger (OpenAPI).
- Database schema managed via JPA.

---

## 🧩 Technologies Used

- **Java:** 17+
- **Spring Boot:** 3.5.4
- **Spring Data JPA** (Hibernate)
- **MapStruct**
- **Lombok**
- **MySQL**
- **SLF4J + Logback** for Logging
- **OpenAPI** (Springdoc)

---

## 🏗️ Directory Structure

```
com.novel.web
├── configuration
│   └── DbConfig.java
├── controller
│   └── NovelController.java
├── domain
│   ├── Novel.java
│   └── NovelDetails.java
├── dto
│   └── request
│       ├── NovelDetailsRequestDTO.java
│       └── NovelRequestDTO.java
├── exception
│   └── NovelException.java
├── mapper
│   └── NovelRequestMapper.java
├── repositories
│   └── NovelRepository.java
├── service
│   ├── NovelService.java
│   └── impl
│       └── NovelServiceImpl.java
└── WebApplication.java
```

---

## ⚙️ How to Run the Project

### ✅ Prerequisites

- Java 17 or later
- Maven 3.6+
- MySQL Server running locally (or modify `application.properties` accordingly)
- IDE like IntelliJ IDEA or VS Code

### ▶️ Steps

1. **Clone the Repository**

```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```

2. **Configure the Database**

Update your `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. **Build the Project**

```bash
mvn clean install
```

4. **Run the Application**

```bash
mvn spring-boot:run
```

The application will be accessible at:

```
http://localhost:8080
```

### 🧪 API Documentation

Once running, Swagger UI (OpenAPI) is available at:

```
http://localhost:8080/swagger-ui.html
```

---

## 🪵 Logging

Logging is enabled at multiple levels across the application:

- **Levels:** `info`, `warn`, `error`
- Uses `SLF4J` with `Logback`
- Logs printed to console

---

## 🧼 Notes

- Avoid adding duplicate novels by name/link. The service layer validates and prevents such inserts.
- Proper exception messages and HTTP status codes are returned for invalid operations.
