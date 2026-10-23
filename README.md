# street-to-casino
A full-stack idle game where you start as a shoe cleaner and build your way to the world's most prestigious casinos.

## Backend

The backend is built with Java 21, Spring Boot and Maven.

It contains the core gameplay mechanics, including shoe cleaning, money management, brush upgrades and automatic shoe generation.

### Requirements

- Java 21

### Run the application

Navigate to the backend directory:

```bash
cd backend
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

The application runs at `http://localhost:8080` by default.

**Note:** No REST endpoints are available yet. A 404 response at the root URL is expected.

### Run the tests

On Windows:

```powershell
.\mvnw.cmd clean test
```

On macOS or Linux:

```bash
./mvnw clean test
```

The tests cover the core gameplay mechanics using JUnit and Mockito.