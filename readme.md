# Inazuma Eleven Express - Application Documentation

## 1. Introduction

This document provides a detailed overview of the Inazuma Eleven Express application, a full-stack project for fans of the Inazuma Eleven series. The application allows users to browse characters, view their stats, and manage their special techniques (Hissatsus).

The project consists of two main components:
1.  A **Kotlin/Spring Boot backend** that serves a REST API and a simple web interface.
2.  A **Jetpack Compose Android application** that consumes the REST API to deliver a native mobile experience.

## 2. High-Level Architecture

The system uses a classic client-server architecture. The Android application is the client, and the Spring Boot application is the backend. Communication occurs exclusively through a **REST API over HTTP**, with data exchanged in **JSON** format.

```
+----------------------------+      HTTP/JSON      +-----------------------------+
|                            |  (REST API Calls)   |                             |
|    Android App (Client)    | <-----------------> | Spring Boot App (Backend)   |
| (Jetpack Compose, MVVM)    |                     | (Kotlin, JPA, MySQL)        |
|                            |                     |                             |
+----------------------------+                     +-----------------------------+
```

## 3. Backend (`inazumaElevenBackend`)

The backend is a Spring Boot application written in Kotlin. It handles all business logic, data persistence, and serving the API that the Android app consumes.

### 3.1. Replicating the Backend Environment

To run the backend, you need to set up its MySQL database using Docker.

**Prerequisites:**
*   Docker and Docker Compose must be installed.

**Step 1: Launch the Database Container**

Navigate to the backend project's root directory (where `docker-compose.yml` is located) and run:

```bash
docker-compose up -d
```

This command starts a MySQL 8.0 container named `mysql-inazuma-server` on port `3306`, configured with the credentials specified in the `.yml` file.

**Step 2: Database Migration (Import/Export)**

The backend requires its database to be populated with data. You will need a `SpringBootInazuma.sql` file.

#### To Import a Database:
1.  Copy your SQL dump file into the running Docker container:
    ```bash
    docker cp SpringBootInazuma.sql mysql-inazuma-server:/tmp/SpringBootInazuma.sql
    ```
2.  Access the container's shell:
    ```bash
    docker exec -it mysql-inazuma-server bash
    ```
3.  Inside the container, run the import command using the credentials from your `.yml` file:
    ```bash
    mysql -u Andrei -p InazumaExpressSpringBoot < /tmp/SpringBootInazuma.sql
    ```
    You will be prompted for the password (`inazuma`).

#### To Export a Database:
1.  Access the container's shell:
    ```bash
    docker exec -it mysql-inazuma-server bash
    ```
2.  Inside the container, dump the database to a temporary file:
    ```bash
    mysqldump --no-tablespaces -u Andrei -p SpringBootInazuma > /tmp/SpringBootInazuma.sql
    ```
3.  Exit the container shell (`exit`).
4.  Copy the SQL file from the container to your local machine:
    ```bash
    docker cp mysql-inazuma-server:/tmp/SpringBootInazuma.sql SpringBootInazuma.sql
    ```

### 3.2. Technology Stack

*   **Framework**: Spring Boot 3
*   **Language**: Kotlin
*   **Database**: MySQL 8.0
*   **Data Persistence**: Spring Data JPA / Hibernate
*   **Build Tool**: Maven

### 3.3. Project Structure

The backend follows a standard layered architecture:

```
inazumaExpressBackend/
├───controller/      # (Web UI) - Handles server-side rendered HTML via Thymeleaf.
├───restController/  # (API)    - Handles RESTful API requests from the Android app.
├───service/         # Contains the core business logic.
├───repository/      # Data Access Layer - Interfaces for database operations.
├───model/           # Defines the data entities (e.g., Character, Hissatsu).
└───config/          # Application configuration (e.g., CORS).
```

### 3.4. REST API (`restController` package)

This is the primary interface for the Android application. Key endpoints include:
*   `CharacterRestController`: `GET /api/characters`, `GET /api/characters/{id}`
*   `HissatsuRestController`: `GET /api/hissatsus`
*   `CharacterHissatsusRestController`: `POST /api/character-hissatsus` (for assignments)
*   `TeamRestController`: Manages team data.

## 4. Frontend (`InazumaElevenExpress2` - Android App)

The frontend is a modern Android application built entirely with Kotlin and Jetpack Compose, following Google's recommended architecture patterns.

### 4.1. Technology Stack

*   **UI Toolkit**: Jetpack Compose
*   **Architecture**: MVVM (Model-View-ViewModel)
*   **Navigation**: Navigation Compose
*   **Dependency Injection**: Hilt
*   **Networking**: Retrofit & OkHttp
*   **Image Loading**: Coil

### 4.2. Architecture (MVVM)

*   **View (Composables)**: Located in `ui/screens`. These are stateless UI functions that receive data from a ViewModel.
*   **ViewModel**: Reside in packages like `ui/screens/home`. They contain presentation logic and expose data to the UI via `StateFlow`.
*   **Model**: Kotlin data classes (`model` package) representing the backend's JSON responses.

**A Note on User Authentication:**
The Android application operates in a public, "guest" mode. It does not require users to log in. Files related to a user-based flow (`LoginScreen.kt`, `RegisterScreen.kt`, `AuthViewModel.kt`) are part of a deprecated implementation and are not used in the primary application journey.

### 4.3. Core Application Flow

1.  **`InitialScreen`**: The app's entry point, providing navigation to the main content.
2.  **`HomeScreen`**: The main landing page, powered by `HomeViewModel`.
3.  **`InazumaCharactersScreen`**: Fetches and displays a grid of all characters.
4.  **`CharacterDetailsScreen`**: Shows comprehensive details for a selected character, including their stats and Hissatsus. It uses `CharacterDetailsViewModel` to manage its state.
5.  **`AssignHissatsuScreen`**: A screen for viewing and assigning Hissatsus to a character.

### 4.4. Data Flow

The flow of data from the backend to the UI is unidirectional and reactive:
1.  A **Composable** screen is displayed.
2.  Its corresponding **ViewModel** is created by Hilt.
3.  The ViewModel calls a **Repository** (which abstracts the data source).
4.  The Repository uses a **Retrofit Service** to make an HTTP call to the backend.
5.  The JSON response is parsed into Kotlin **Model** objects.
6.  The ViewModel exposes this data as a `StateFlow`.
7.  The Composable collects the `StateFlow` and automatically updates the UI.
