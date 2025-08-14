# 📚 Novel Management System (Spring Boot)

A backend system to manage novels using **Spring Boot**, **JPA**, **MapStruct**, and **DTO-based architecture**.  
This application supports CRUD operations, prevents duplicate entries, and provides clean API documentation using Swagger (OpenAPI).

---

## 🚀 Features

- ➕ Add novels with **unique names** and links.
- 🔍 **Duplicate prevention** at the service layer.
- 🗄 Store **novel descriptions** in a separate table.
- 🔄 **DTO ↔ Entity mapping** using MapStruct.
- 🧹 **Clean architecture** with separation of Controller, Service, and Repository layers.
- 🌍 **Global exception handling** for consistent API error responses.
- 🪵 Logging using **SLF4J (Logback)**.
- 📜 Interactive API documentation via **Swagger**.
- 🗃 Database schema managed via **JPA** (Hibernate).

---

## 🧩 Technologies Used

- **Java:** 17+
- **Spring Boot:** 3.5.4
- **Spring Data JPA** (Hibernate)
- **MapStruct** for DTO mapping
- **Lombok** for boilerplate code reduction
- **MySQL** as the database
- **SLF4J + Logback** for logging
- **OpenAPI (Springdoc)** for documentation
- **JUnit 5** & **Mockito** for testing

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

- Java **17** or later
- Maven **3.6+**
- MySQL Server (local or remote)
- IDE: IntelliJ IDEA / VS Code / Eclipse

### ▶️ Steps

1. **Clone the Repository**

   ```bash
   git clone https://github.com/vikasyadav17/Novel-management-backend.git
   cd Novel-management-backend.
   ```

2. **Configure the Database**  
   Update `src/main/resources/application.properties`:

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

5. **Access the Application**
   ```
   http://localhost:8080
   ```

---

## 🧪 API Documentation

Once the application is running, access **Swagger UI** at:

```
http://localhost:8080/swagger-ui.html
```

---

## 📤 Postman Collection

To quickly test all the endpoints, you can import the provided **Postman Collection**.

### 📥 Import Steps:

1. Download the `NovelManagementSystem.postman_collection.json` file from the repository.
2. Open **Postman** → Click **Import**.
3. Select the downloaded file.
4. All APIs will appear under the "Novel Management System" collection.

---

## 🧪 Testing

Unit and integration tests are written using **JUnit 5** and **Mockito** to ensure code quality and correctness.

## 🪵 Logging

Logging is enabled at multiple levels across the application:

- **Levels:** `info`, `warn`, `error`
- Uses `SLF4J` with `Logback`
- Logs printed to console

---

## 🧼 Notes

- Avoid adding duplicate novels by name/link. The service layer validates and prevents such inserts.
- Proper exception messages and HTTP status codes are returned for invalid operations.
