# University Management System

## Overview

University Management System is a REST API application developed using Spring Boot and PostgreSQL.

The application manages three main entities:
- Students
- Courses
- Grades

The main purpose of this project is to demonstrate how a single Spring Boot application can connect to and manage three independent PostgreSQL databases.

The system also provides a Student Transcript API that combines information from all three databases.

## Technologies

- Java 21+
- Spring Boot 4.1.2
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Jakarta Bean Validation
- Lombok
- Postman

## Database Architecture

The application uses three independent PostgreSQL databases:

| Database | Purpose |
|---|---|
| `student_db` | Stores student information |
| `course_db` | Stores course information |
| `grade_db` | Stores student grades |

Each database has its own configuration:

- DataSource
- EntityManagerFactory
- TransactionManager
- JPA Repository configuration

This separation allows each database to be managed independently.

The `grades` table stores `student_id` and `course_id` as logical references. Since the data is stored in separate databases, the application validates these references through the service layer instead of using cross-database foreign keys.

## Project Structure

```text
src/main/java/UniversityManagement/
├── config/
│   ├── StudentDatabaseConfig.java
│   ├── CourseDatabaseConfig.java
│   └── GradeDatabaseConfig.java
├── student/
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   └── service/
├── course/
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   └── service/
├── grade/
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   └── service/
├── dto/
├── exception/
└── UniversityManagementApplication.java

database/
├── 01_student_db.sql
├── 02_course_db.sql
└── 03_grade_db.sql
```

## Database Setup

Make sure PostgreSQL is installed and running.

Create three databases:

```sql
CREATE DATABASE student_db;
CREATE DATABASE course_db;
CREATE DATABASE grade_db;
```

Run the SQL scripts from the `database` directory:

```bash
psql -U qwokkaq -d student_db -f database/01_student_db.sql

psql -U qwokkaq -d course_db -f database/02_course_db.sql

psql -U qwokkaq -d grade_db -f database/03_grade_db.sql
```

These scripts create the required database tables.

## Configuration

Database connection settings are located in:

`src/main/resources/application.properties`

The application uses environment variables:

| Variable | Description |
|---|---|
| `DB_USER` | PostgreSQL username |
| `DB_PASSWORD` | PostgreSQL password |

Default username: `qwokkaq`.

For example, on macOS or Linux:

```bash
export DB_USER=qwokkaq
export DB_PASSWORD=your_password
```

Update the default username if your local PostgreSQL configuration uses a different account.

## Running the Application

Requirements:
- Java 21 or newer
- Maven
- PostgreSQL

Clone the repository:

```bash
git clone https://github.com/qwokkaq/uni-management.git
cd uni-management
```

Start the application:

```bash
./mvnw spring-boot:run
```

Alternatively, if Maven is installed:

```bash
mvn spring-boot:run
```

The application runs at:

`http://localhost:8080`

Hibernate is configured to automatically update the database schema.

## REST API Endpoints

### Students API

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Create a student |
| PUT | `/api/students/{id}` | Update a student |
| DELETE | `/api/students/{id}` | Delete a student |

### Courses API

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/courses` | Get all courses |
| GET | `/api/courses/{id}` | Get course by ID |
| POST | `/api/courses` | Create a course |
| PUT | `/api/courses/{id}` | Update a course |
| DELETE | `/api/courses/{id}` | Delete a course |

### Grades API

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/grades` | Get all grades |
| GET | `/api/grades/{id}` | Get grade by ID |
| GET | `/api/grades/student/{studentId}` | Get grades by student |
| GET | `/api/grades/course/{courseId}` | Get grades by course |
| POST | `/api/grades` | Create a grade |
| PUT | `/api/grades/{id}` | Update a grade |
| DELETE | `/api/grades/{id}` | Delete a grade |

### Student Transcript API

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students/{studentId}/transcript` | Get a student's academic transcript |

The transcript endpoint retrieves:
- Student information from `student_db`
- Course information from `course_db`
- Grades from `grade_db`

It also calculates:
- Total credits
- Average score

## Validation and Exception Handling

The application uses Jakarta Bean Validation and centralized exception handling.

Validation examples:
- Student email must have a valid format.
- Required fields cannot be empty.
- Course credits must be between 1 and 30.
- Grade scores must be between 0 and 100.
- Student and course IDs must reference existing records.

HTTP status codes include:

| Status | Description |
|---|---|
| 200 | OK |
| 201 | Created |
| 400 | Bad Request |
| 404 | Not Found |
| 409 | Conflict |

## Testing

The REST API can be tested using Postman.

The following functionality has been tested:
- Student CRUD operations
- Course CRUD operations
- Grade CRUD operations
- Input validation
- Student and course reference validation
- Transcript generation
- Handling of non-existing resources

## Author

University Management System — Laboratory Work Project.