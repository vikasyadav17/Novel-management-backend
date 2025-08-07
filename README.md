# 📚 Novel Management System (Spring Boot)

A backend system to manage Novels using Spring Boot, JPA, MapStruct, and DTO architecture.

---

## 🚀 Features

- Add novels with unique names
- Prevent duplicates using service-layer checks
- Store novel descriptions in a separate table
- DTO to Entity mapping with MapStruct
- Clean separation between Controller, Service, and Repository
- Logging using SLF4J (`LoggerFactory`)
- Database schema handled via JPA

---

## 🧩 Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- MapStruct
- Lombok
- MySQL
- SLF4J + Logback for Logging

---

## 🪵 Logging

- Enabled across controller, service, and exception handler
- Uses `LoggerFactory.getLogger(...)`
- Levels: `info`, `debug`, `warn`, `error`
- Logs saved to console and optionally to file

---

## 📁 Directory Structure

```plaintext
com.novel
├── controller
│   └── NovelController.java
├── domain
│   ├── Novel.java
│   └── NovelDetails.java
├── dto
│   └── NovelRequestDTO.java
├── mapper
│   └── NovelRequestMapper.java
├── repositories
│   └── NovelRepository.java
├── service
│   ├── NovelService.java
│   └── impl
│       └── NovelServiceImpl.java
└── exception
    └── GlobalExceptionHandler.java
```
