# Mortgage Backend Assessment

## Overview
Spring Boot backend implementing mortgage interest rates and mortgage feasibility checks.
The application is intentionally kept simple and focused on the assignment requirements.

---

## Tech Stack
- Java 21
- Spring Boot 3.x
- Spring Web
- Bean Validation (Jakarta)
- Spring Cache (in-memory)
- SLF4J Logging
- Swagger / OpenAPI
- JUnit 5

---

## Covered
- API versioning (`/api/v1`)
- Interest rates API (in-memory on startup)
- Mortgage check with business rules
- Validation & centralized error handling
- In-memory caching
- Logging at service and error level
- Swagger/OpenAPI documentation
- Minimal Docker Compose setup (optional)

---

## Run the Application

### Local (Maven)
```bash
mvn clean install
mvn spring-boot:run
```

Application starts at:
http://localhost:8080

---

### Docker Compose (Optional)
```bash
docker-compose up -d
```

> Docker is optional and provided only for local convenience.
> The application does not require any external dependencies.

---

## API Endpoints

### Get Interest Rates
GET /api/v1/interest-rates

Returns the list of available mortgage interest rates.
Rates are created in memory during application startup.

---

### Check Mortgage Feasibility
POST /api/v1/mortgage-check

Request example:
```json
{
  "income": 40000,
  "maturityPeriod": 20,
  "loanValue": 150000,
  "homeValue": 200000
}
```

Response example:
```json
{
  "feasible": true,
  "monthlyCosts": 869.94
}
```

---

## Business Rules
- Mortgage is feasible if:
  - loanValue ≤ 4 × income
  - loanValue ≤ homeValue
- Monthly cost is calculated using the interest rate associated with the selected maturity period.
- Monthly cost represents an interest-only calculation as required by the assignment.
- Mortgage Calcultion formula
  M = P × ( r × (1 + r)^n ) / ( (1 + r)^n − 1 )
  
  M = Monthly mortgage payment
  P = Principal (loan amount)
  r = Monthly interest rate (annualRate / 12 / 100)
  n = Total number of payments (years × 12)

  factor = (1 + r)^n ##represents the compound interest growth over the entire loan duration
---

## Swagger
Swagger UI:
http://localhost:8080/swagger-ui/index.html

---

## Notes
- The application is stateless
- Scope intentionally limited to match a 4–5 hour coding assessment
- Given more time, the following enhancements would be considered:
  - Advanced security (OAuth2 / JWT)
  - Distributed caching
  - Observability (metrics, tracing, health dashboards)
  - CI/CD pipeline integration

## Author
Developed as part of a coding assignment.  
Maintainer: **Rashmi Napanda Naniyappa**
