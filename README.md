# Vive Medellín API (Spring Boot + PostgreSQL + JWT)

Backend en Spring Boot 3 (Java 17) con autenticación JWT, PostgreSQL y JPA/Hibernate. Actualmente Flyway está deshabilitado y el esquema se gestiona con Hibernate (create-drop).

## Stack
- Java 17+, Spring Boot 3, Maven
- Spring Web, Spring Data JPA, Spring Security, Validation
- PostgreSQL
- JJWT (HS256)
- Lombok (requiere annotation processing en el IDE)
- Actuator (health)

## Requisitos
- Java (JDK) 17+
- Maven 3.9+
- Docker (para DB local) o una instancia de PostgreSQL

## Arranque rápido
1) Base de datos con Docker Compose:
```zsh
cd /Users/yiyi/viveMedellin/api
# Si ves un warning por 'version:' es inocuo en Compose v2
docker compose up -d
docker compose ps
```

2) Ejecutar la app (Maven):
```zsh
cd /Users/yiyi/viveMedellin/api
export DB_URL=jdbc:postgresql://localhost:5432/eventos
export DB_USER=eventos
export DB_PASS=eventos
# Recomendado: definir una clave JWT robusta
export JWT_SECRET=$(python3 - <<'PY'
import secrets; print(secrets.token_urlsafe(64))
PY
)

mvn -DskipTests spring-boot:run
```

3) Verificar salud:
```zsh
curl -fsS http://localhost:8080/actuator/health
# {"status":"UP"}
```

## Configuración (application.yml)
- JPA/Hibernate: `ddl-auto: create-drop` (crea y elimina el esquema al iniciar/detener la app)
- Flyway: `enabled: false`
- Dialect: `org.hibernate.dialect.PostgreSQLDialect`
- Variables de entorno soportadas:
  - DB_URL (por defecto `jdbc:postgresql://localhost:5432/eventos`)
  - DB_USER (por defecto `eventos`)
  - DB_PASS (por defecto `eventos`)
  - JWT_SECRET (por defecto `changeme`; cambiar fuera de local)
  - CORS_ALLOWED_ORIGINS (por defecto `http://localhost:5173`)

## Ejecutar con JAR
```zsh
cd /Users/yiyi/viveMedellin/api
mvn -DskipTests clean package
java -jar target/eventos-0.0.1-SNAPSHOT.jar
```
Cambiar puerto:
```zsh
mvn -DskipTests spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

## Endpoints
- Health (libre):
  - `GET /actuator/health` → `{ "status": "UP" }`

- Auth (libres):
  - `POST /api/auth/signup` → 200 sin cuerpo (crea usuario)
    - Body: `{ "name", "username", "email", "password" }`
  - `POST /api/auth/login` → 200 `{ accessToken, refreshToken, user }`
    - Body: `{ "username", "password" }`
  - `POST /api/auth/refresh` → 200 con nuevo `accessToken`
    - Body: `{ "refreshToken" }`
  - `GET /api/auth/allUsers` → lista de usuarios

- Events (ACTUALMENTE públicos según SecurityConfig):
  - `GET /api/events?page=0&size=10` → lista de `EventSummaryDto`
  - `GET /api/events/{id}` → `EventDto`
  - `POST /api/events/save` → crea evento a partir de `EventDto`
  - `PUT /api/events/update` → actualiza y devuelve `EventDto`
  - `DELETE /api/events/{id}` → 204 sin contenido
  - `GET /api/events/user/{userId}` → lista de `EventDto` del usuario

> Nota: si se desea proteger los endpoints de eventos, actualizar `SecurityConfig` para exigir autenticación (quitar `permitAll()` en `/api/events/**`).

## Ejemplos (curl)
Signup (200 sin cuerpo):
```zsh
curl -i -X POST http://localhost:8080/api/auth/signup \
  -H 'Content-Type: application/json' \
  -d '{"name":"Demo","username":"demo","email":"demo@local","password":"Demo123!"}'
```

Login y usar token:
```zsh
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo","password":"Demo123!"}' | \
  python3 -c 'import sys, json; print(json.load(sys.stdin)["accessToken"])')

# Si proteges eventos en el futuro:
curl -s http://localhost:8080/api/events -H "Authorization: Bearer $TOKEN"
```

## Troubleshooting
- Puerto 8080 en uso:
```zsh
lsof -ti tcp:8080 | xargs kill -9
```
- Docker Compose: ejecuta desde `api/` o usa `-f /ruta/al/docker-compose.yml`.
- DB inaccesible: revisa `docker compose ps`, credenciales y `DB_URL`.
- 403 en endpoints protegidos: falta `Authorization: Bearer <accessToken>` o token inválido.

## Notas
- `target/` es artefacto de build; se puede borrar y se regenera con Maven.
- Lombok: si el IDE no reconoce getters/setters, habilitar "Annotation processing".
