# services

Microservicios de negocio del proyecto. Cada carpeta es un proyecto Spring Boot independiente, con su propia base de datos PostgreSQL y su propio Maven Wrapper.

## Antes de arrancar un servicio

1. Levanta la infraestructura en DEV, en este orden: `config`, `eureka` y `gateway` (ver [`infra/README.md`](../infra/README.md)). Los servicios leen su configuración de `config` (`http://localhost:18888`) y se registran en `eureka`.
2. Docker Desktop encendido: en DEV solo se usa para PostgreSQL. La aplicación corre con Maven Wrapper en el host.

## Servicios

| Carpeta | Qué es | Puerto DEV (app) | Base de datos DEV |
|---|---|---|---|
| `lms-ms/` | Experiencia de aprendizaje: cursos, contenidos y matrículas. Valida por Feign + Circuit Breaker el estado de cuenta en `ingresos-pagos-ms` antes de confirmar una matrícula. | `8080` (2.ª instancia: `8081`) | `lms_db` en `15432` |
| `ingresos-pagos-ms/` | Finanzas: estado de cuenta del estudiante y pagos. Consume `matricula.creada` y publica `pago.validado`. | `8082` (2.ª instancia: `8083`) | `ingresos_pagos_db` en `15433` |
| `auth-ms/` | Autenticación y emisión de JWT (temporal, luego Keycloak). | `8085` | `auth_db` en `15431` |

## Ejecutar en DEV

Cada comando se ejecuta parado en la carpeta del servicio. Primero la base de datos, después la aplicación.

### `lms-ms`

```powershell
cd services/lms-ms
docker compose -f compose-dev.yml up -d
.\mvnw.cmd spring-boot:run
```

Swagger: `http://localhost:8080/swagger-ui.html`. Health: `http://localhost:8080/actuator/health`.

Segunda instancia, en otra terminal (puerto fijo, no aleatorio):

```powershell
cd services/lms-ms
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

### `ingresos-pagos-ms`

```powershell
cd services/ingresos-pagos-ms
docker compose -f compose-dev.yml up -d
.\mvnw.cmd spring-boot:run
```

Swagger: `http://localhost:8082/swagger-ui.html`. Health: `http://localhost:8082/actuator/health`. Necesita Kafka arriba (ver [`../kafka/README.md`](../kafka/README.md)) para consumir `matricula.creada` y publicar `pago.validado`.

Segunda instancia:

```powershell
cd services/ingresos-pagos-ms
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"
```

### `auth-ms`

```powershell
cd services/auth-ms
docker compose -f compose-dev.yml up -d
.\mvnw.cmd spring-boot:run
```

Detalle de cada endpoint en el `README.md` propio de cada carpeta (`lms-ms/README.md`, `ingresos-pagos-ms/README.md`).
