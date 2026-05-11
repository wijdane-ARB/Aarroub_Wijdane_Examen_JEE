# 🚗 AutoRent Pro — Backend

> Spring Boot REST API for the **Vehicle Rental Management** platform.  
> Handles agencies, vehicles, rentals, and JWT-secured role-based access control.

---

## 📦 Tech Stack

| Concern | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.6 |
| Persistence | Spring Data JPA · Hibernate · H2 (dev) / MySQL / PostgreSQL (prod) |
| Security | Spring Security · JWT (HS256) |
| Mapping | Lombok · ModelMapper / manual mappers |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build | Maven 3.8+ |

---

## 🗂 Project Structure

```
backend/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── ma/enset/aarroub/wijdane/aarroub_wijdane_exam_jee/
    │   │       │
    │   │       ├── AarroubWijdaneExamJeeApplication.java   # @SpringBootApplication + CommandLineRunner (seed data)
    │   │       │
    │   │       ├── entities/
    │   │       │   ├── Agence.java       # Agency — id, nom, adresse, ville, telephone
    │   │       │   ├── Vehicle.java      # Abstract base — Single-Table Inheritance (@Inheritance STI)
    │   │       │   ├── Voiture.java      # discriminator = "VOI" — nombrePortes, typeCarburant, boiteVitesse
    │   │       │   ├── Moto.java         # discriminator = "MOT" — cylindree, typeMoto, casqueInclus
    │   │       │   └── Location.java     # Rental record — dateDebut, dateFin, montantTotal
    │   │       │
    │   │       ├── enums/
    │   │       │   └── VehicleStatus.java   # DISPONIBLE | LOUE | EN_MAINTENANCE
    │   │       │
    │   │       ├── repositories/
    │   │       │   ├── AgenceRepository.java     # JPA repository for Agence
    │   │       │   ├── VehicleRepository.java    # JPA repository for Vehicle (covers Voiture & Moto)
    │   │       │   └── LocationRepository.java   # JPA repository for Location
    │   │       │
    │   │       ├── dtos/
    │   │       │   ├── AgenceDTO.java     # Agency response payload
    │   │       │   ├── VehicleDTO.java    # Vehicle response payload (shared for Voiture & Moto)
    │   │       │   ├── AuthRequest.java   # { username, password }
    │   │       │   └── AuthResponse.java  # { token }
    │   │       │
    │   │       ├── mappers/
    │   │       │   └── RentalMapper.java   # Entity ↔ DTO conversion
    │   │       │
    │   │       ├── services/
    │   │       │   └── RentalService.java   # Business logic (single class, no separate interface)
    │   │       │
    │   │       ├── security/
    │   │       │   ├── SecurityConfig.java            # Filter chain, in-memory users, role rules
    │   │       │   ├── JwtUtil.java                   # Token generation & parsing (HS256)
    │   │       │   ├── JwtAuthorizationFilter.java    # OncePerRequestFilter — validates JWT on each request
    │   │       │   └── CorsConfig.java                # CORS bean (supplements SecurityConfig)
    │   │       │
    │   │       └── web/
    │   │           ├── AuthController.java        # POST /api/auth/login
    │   │           └── RentalRestController.java  # GET|POST /api/agences, GET /api/agences/{id}/vehicles, GET /api/vehicles/{id}
    │   │
    │   └── resources/
    │       └── application.properties   # H2 console, datasource, JWT config
    │
    └── test/
        └── java/ ...   # Test classes
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** — [Download](https://adoptium.net/)
- **Maven 3.8+** (or use the included `./mvnw` wrapper)

### Run the application

```bash
# Clone the repo
git clone https://github.com/WijdaneAarroub/AarroubWijdane-Exam-JEE.git
cd AarroubWijdane-Exam-JEE/backend

# Start with embedded Maven wrapper
./mvnw spring-boot:run
# Windows:
mvnw.cmd spring-boot:run
```

The server starts at **`http://localhost:8080`**.

### H2 Console (development)

| Field | Value |
|---|---|
| URL | `http://localhost:8080/h2-console` |
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | *(empty)* |

### Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

Click **Authorize**, paste your JWT token (obtained from `/api/auth/login`), then test any endpoint interactively.

---

## 🔐 Authentication

All endpoints except `/api/auth/login` require a Bearer token.

```http
Authorization: Bearer <jwt_token>
```

### Demo users

| Username | Password | Role |
|---|---|---|
| `admin1` | `pass` | `ROLE_ADMIN` |
| `employe1` | `pass` | `ROLE_EMPLOYE` |
| `client1` | `pass` | `ROLE_CLIENT` |

### Login request

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{ "username": "admin1", "password": "pass" }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## 📡 API Reference

### Auth

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/api/auth/login` | Public | Login — returns JWT |

### Agencies

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/api/agences` | CLIENT · EMPLOYÉ · ADMIN | List all agencies |
| `POST` | `/api/agences` | ADMIN | Create a new agency |
| `GET` | `/api/agences/{id}` | CLIENT · EMPLOYÉ · ADMIN | Get agency by ID |
| `DELETE` | `/api/agences/{id}` | ADMIN | Delete an agency |

### Vehicles

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/api/agences/{id}/vehicles` | EMPLOYÉ · ADMIN | Vehicles of an agency |
| `GET` | `/api/vehicles/{id}` | EMPLOYÉ · ADMIN | Get a vehicle by ID |
| `POST` | `/api/vehicles` | ADMIN | Add a vehicle |
| `DELETE` | `/api/vehicles/{id}` | ADMIN | Remove a vehicle |

### Rentals

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/api/locations` | EMPLOYÉ · ADMIN | List all rentals |
| `POST` | `/api/locations` | CLIENT · EMPLOYÉ · ADMIN | Create a rental |

---

## 🗃 Data Model

```
Agence (1) ──────< (N) Vehicle
                           │
                    ┌──────┴──────┐
                  Voiture       Moto
                           │
Vehicle (1) ──────< (N) Location
```

**Single-Table Inheritance** — `Voiture` and `Moto` share the `vehicle` table, discriminated by a `DTYPE` column. This keeps queries simple while preserving polymorphism.

---

## ⚙️ Configuration

`src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# H2 (development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop

# JWT
jwt.secret=your_secret_key_here
jwt.expiration=86400000

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui/index.html
```

### Switching to MySQL / PostgreSQL

Replace the H2 block with:

```properties
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/vehicle_rental
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

---

## 🌱 Sample Data (CommandLineRunner)

On startup, `AarroubWijdaneExamJeeApplication` runs a `CommandLineRunner` bean that seeds the database with:

- Several **agencies** across different Moroccan cities
- A mix of **Voiture** and **Moto** vehicles with varied statuses (`DISPONIBLE`, `LOUE`, `EN_MAINTENANCE`)
- **3 in-memory users** — one per role — defined in `SecurityConfig` via `InMemoryUserDetailsManager`

---

## 🛠 Troubleshooting

| Symptom | Fix |
|---|---|
| `403 Forbidden` | You're not sending a valid JWT. Login first and include `Authorization: Bearer <token>`. |
| Backend won't start | Make sure Java 21 is active: `java -version`. |
| H2 console blank | Confirm `spring.h2.console.enabled=true` and use JDBC URL `jdbc:h2:mem:testdb`. |
| Swagger shows 401 | Click **Authorize** in Swagger UI and paste your JWT (without the `Bearer` prefix). |

---

## 🔭 Roadmap & Future Enhancements

The items below are **not yet implemented**. They represent the natural next steps to turn this exam project into a production-grade application.

---

### 🔒 Security

| # | Enhancement | Notes |
|---|---|---|
| 1 | **Refresh tokens** | Issue a short-lived access token (15 min) alongside a long-lived refresh token (7 days) stored in an `HttpOnly` cookie. Add `POST /api/auth/refresh` and `POST /api/auth/logout` endpoints. |
| 2 | **Persistent users in DB** | Replace `InMemoryUserDetailsManager` with a `User` entity + `UserRepository`. Hash passwords with **BCrypt** via `PasswordEncoder`. |
| 3 | **User registration endpoint** | `POST /api/auth/register` — allows self-service sign-up with `ROLE_CLIENT` by default. |
| 4 | **Account management** | Endpoints to update email/password, enable/disable accounts (admin only). |
| 5 | **Rate limiting** | Use **Bucket4j** or Spring's `RateLimiter` to throttle `/api/auth/login` against brute-force attacks. |

---

### 🗃 Data & Persistence

| # | Enhancement | Notes |
|---|---|---|
| 6 | **Pagination & filtering** | Add `Pageable` support to `GET /api/agences` and `GET /api/agences/{id}/vehicles`. Accept query params `?page=0&size=10&status=DISPONIBLE&sort=prixParJour,asc`. |
| 7 | **Full rental lifecycle** | Complete `Location` CRUD: `PUT /api/locations/{id}/return` to mark a vehicle returned, auto-update its status to `DISPONIBLE`. |
| 8 | **Soft delete** | Add `deletedAt` timestamp to entities. Filter deleted records out of all queries with `@Where(clause = "deleted_at IS NULL")`. |
| 9 | **Audit logging** | Integrate **Spring Data Envers** (`@Audited`) to record who created/modified/deleted each record and when. |
| 10 | **Redis caching** | Cache `GET /api/agences` and per-agency vehicle lists with `@Cacheable`. Evict on mutations with `@CacheEvict`. |
| 11 | **Flyway migrations** | Replace `hibernate.ddl-auto=create-drop` with **Flyway** versioned SQL scripts (`V1__init.sql`, `V2__seed.sql`) for repeatable, tracked schema evolution. |
| 12 | **Additional enums** | Extract `typeCarburant` (ESSENCE, DIESEL, HYBRIDE, ELECTRIQUE), `boiteVitesse` (MANUELLE, AUTOMATIQUE), and `typeMoto` (SPORTIVE, SCOOTER, ROADSTER, TOURING) from plain strings into proper `@Enumerated(EnumType.STRING)` fields, mirroring the existing `VehicleStatus` pattern. |

---

### 📊 Business Features

| # | Enhancement | Notes |
|---|---|---|
| 12 | **Statistics endpoints** | `GET /api/stats/revenue` — total revenue per agency per month. `GET /api/stats/top-vehicles` — most rented vehicles. Aggregate with JPQL or native queries. |
| 13 | **PDF invoice generation** | On rental creation, generate a PDF receipt using **iText** or **JasperReports** and return it as `application/pdf`. |
| 14 | **Email notifications** | Send a booking confirmation email via **Spring Mail** (SMTP / SendGrid) when a location is created or a vehicle returned. |
| 15 | **Vehicle photo upload** | `POST /api/vehicles/{id}/photo` — accept `multipart/form-data`, store on disk or **AWS S3**, return a public URL saved in the entity. |
| 16 | **Availability calendar** | `GET /api/vehicles/{id}/availability?from=2025-01-01&to=2025-01-31` — return booked date ranges so the frontend can show a calendar picker. |

---

### ⚙️ Infrastructure & Quality

| # | Enhancement | Notes |
|---|---|---|
| 17 | **Docker & Docker Compose** | Containerise the app. `docker-compose.yml` with services `backend`, `mysql` (or `postgres`), and optionally `redis`. |
| 18 | **CI/CD with GitHub Actions** | `.github/workflows/ci.yml` — on every push: compile, run tests, build Docker image, push to registry. |
| 19 | **Unit & integration tests** | Service layer with **JUnit 5 + Mockito**. Repository layer with **@DataJpaTest**. Full stack with **Testcontainers** + a real MySQL container. |
| 20 | **Spring Actuator** | Expose `/actuator/health`, `/actuator/info`, `/actuator/metrics`. Secure non-health endpoints to `ROLE_ADMIN`. |
| 21 | **Global exception handler** | `@RestControllerAdvice` returning consistent `{ "status", "message", "timestamp" }` JSON for all errors (404, 400, 403, 500). |
| 22 | **Request validation** | Add `@Valid` + Jakarta Bean Validation (`@NotBlank`, `@Min`, `@Future`) on all incoming DTOs with a unified error response body. |

---



---

*Part of the **AarroubWijdane-Exam-JEE** monorepo — see the [frontend README](../frontend/README.md) for the Angular client.*
