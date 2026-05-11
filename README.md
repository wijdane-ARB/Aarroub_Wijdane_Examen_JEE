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
src/main/java/ma/enset/aarroub/wijdane/aarroub_wijdane_exam_jee/
│
├── entities/
│   ├── Agence.java            # Agency entity
│   ├── Vehicle.java           # Abstract base vehicle (Single-Table Inheritance)
│   ├── Voiture.java           # Car subclass (portes, carburant, boite)
│   ├── Moto.java              # Motorcycle subclass (cylindree, typeMoto, casque)
│   └── Location.java          # Rental record
│
├── enums/
│   ├── VehicleStatus.java     # DISPONIBLE | LOUE | EN_MAINTENANCE
│   ├── TypeCarburant.java     # ESSENCE | DIESEL | HYBRIDE | ELECTRIQUE
│   ├── BoiteVitesse.java      # MANUELLE | AUTOMATIQUE
│   └── TypeMoto.java          # SPORTIVE | SCOOTER | ROADSTER | TOURING
│
├── repositories/
│   ├── AgenceRepository.java
│   ├── VehicleRepository.java
│   └── LocationRepository.java
│
├── dtos/
│   ├── AgenceDTO.java
│   ├── VehicleDTO.java
│   ├── VoitureDTO.java
│   ├── MotoDTO.java
│   ├── LocationDTO.java
│   ├── AuthRequest.java
│   └── AuthResponse.java
│
├── mappers/
│   └── RentalMapper.java      # Entity <-> DTO conversion
│
├── services/
│   ├── RentalService.java     # Service interface
│   └── RentalServiceImpl.java # Business logic implementation
│
├── security/
│   ├── SecurityConfig.java        # Filter chain & role-based rules
│   ├── JwtUtil.java               # Token generation & validation
│   ├── JwtAuthorizationFilter.java# Request filter
│   └── CorsConfig.java            # CORS configuration
│
└── web/
    ├── AuthController.java        # POST /api/auth/login
    └── RentalRestController.java  # Agences & Vehicles endpoints
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

On startup the application automatically seeds:

- **4 agencies** — Casablanca, Rabat, Marrakech, Tanger
- **8 vehicles** — mix of `Voiture` and `Moto` with varied statuses
- **3 users** — one per role (in-memory via `InMemoryUserDetailsManager`)

---

## 🛠 Troubleshooting

| Symptom | Fix |
|---|---|
| `403 Forbidden` | You're not sending a valid JWT. Login first and include `Authorization: Bearer <token>`. |
| Backend won't start | Make sure Java 21 is active: `java -version`. |
| H2 console blank | Confirm `spring.h2.console.enabled=true` and use JDBC URL `jdbc:h2:mem:testdb`. |
| Swagger shows 401 | Click **Authorize** in Swagger UI and paste your JWT (without the `Bearer` prefix). |

---


---

*Part of the **AarroubWijdane-Exam-JEE** monorepo — see the [frontend README](../frontend/README.md) for the Angular client.*
