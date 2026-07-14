# Task Manager API

A RESTful Task Management API built with Spring Boot that allows users to manage users, projects, and tasks.

## Features

- CRUD operations for Users
- CRUD operations for Projects
- CRUD operations for Tasks
- RESTful API design
- Bean Validation using Jakarta Validation
- Global exception handling with `@ControllerAdvice`
- JPA/Hibernate entity relationships

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven

## Project Structure

```
src
├── controllers
├── services
├── repositories
├── entities
├── exceptions
└── resources
```

## Entity Relationships

- One User can have many Projects.
- One User can have many Tasks.
- One Project can have many Tasks.
- Every Project belongs to one User.
- Every Task belongs to one User.
- Every Task belongs to one Project.

## API Endpoints

### Users

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users` | Create a user |
| GET | `/users` | Get all users |
| GET | `/users/{id}` | Get user by ID |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |

### Projects

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/projects` | Create a project |
| GET | `/projects` | Get all projects |
| GET | `/projects/{id}` | Get project by ID |
| PUT | `/projects/{id}` | Update project |
| DELETE | `/projects/{id}` | Delete project |

### Tasks

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/tasks` | Create a task |
| GET | `/tasks` | Get all tasks |
| GET | `/tasks/{id}` | Get task by ID |
| PUT | `/tasks/{id}` | Update task |
| DELETE | `/tasks/{id}` | Delete task |

## Validation

- User name, email, and password are required.
- Project name is required.
- Task title, status, and priority are required.

## Exception Handling

The application uses global exception handling with `@ControllerAdvice`.

Implemented custom exceptions:

- `UserNotFoundException`
- `ProjectNotFoundException`
- `TaskNotFoundException`

