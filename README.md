# Student-Course Management System — Backend (Spring Boot)

This repository contains the Spring Boot backend for managing students and their enrollments in courses. It exposes a REST API consumed by a React + TypeScript frontend.

## Technologies Used

- **Java 21 + Spring Boot**: Production-grade REST API framework.
- **Spring Data JPA (Hibernate)**: Object-relational mapping and repository abstraction.
- **H2 Database**: In-memory database for development and testing.
- **DTO Mapping**: Custom DTOs used to decouple database entities from API responses.
- **Exception Handling**: Returns structured HTTP responses with appropriate status codes.

## Project Structure
```
studentCourseBackend/
├─ controller/           # REST controllers for Students, Courses, Enrollments
├─ dto/                  # Data Transfer Objects
├─ model/                # JPA entity classes
├─ repository/           # JPA repositories (interfaces)
├─ service/              # Business logic and coordination layer
└─ StudentCoursesApplication.java
```

## Features
- CRUD operations for `Student` and `Course`
- Enroll a student in a course using the `Enrollment` entity
- View all students in a course, or all courses for a student
- Search endpoints using JPQL by name, course code/title, or associations
- Validates and prevents duplicate enrollments

## REST API Overview
| Endpoint | Method | Description |
|---|---|---|
| `/api/students` | GET | List all students |
| `/api/courses` | GET | List all courses |
| `/api/students/{id}` | GET | Get student by ID |
| `/api/courses/{id}` | GET | Get course by ID |
| `/api/students` | POST | Create a new student |
| `/api/courses` | POST | Create a new course |
| `/api/enrollments` | POST | Enroll student in course |
| `/api/students/search` | GET | Search students by name or course |
| `/api/courses/search` | GET | Search courses by title/code or student name |

## Setup
1. Clone the repository:
```bash
git clone https://github.com/fssonca/studentCourseBackend.git
cd studentCourseBackend
```

2. Run the application:
```bash
./gradlew bootRun
```

The backend should be running at [http://localhost:8080](http://localhost:8080).

## Frontend
Use the [Student-Course Frontend](https://github.com/fssonca/studentCourseFrontend) to interact with the API.

---

> **Author**: Fernando Sousa  
> [GitHub: fssonca](https://github.com/fssonca)
