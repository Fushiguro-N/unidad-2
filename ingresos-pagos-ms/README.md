# ingresos-pagos-ms

Microservicio financiero. Gestiona el estado de cuenta del estudiante y los pagos de matrícula. Consume el evento `matricula.creada` publicado por [`lms-ms`](../lms-ms), registra el `Pago` correspondiente y publica `pago.validado`. Expone `GET /api/v1/estudiantes/{id}/estado-cuenta`, consultado por `lms-ms` vía Feign + Circuit Breaker antes de confirmar una matrícula.

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/v1/estudiantes/{id}/estado-cuenta` | Indica si el estudiante tiene deuda pendiente (algún `Pago` propio en un estado distinto de `VALIDADO`). Lo consulta `lms-ms` por Feign. |
| `GET` | `/api/v1/pagos` | Lista pagos registrados. |
| `GET` | `/api/v1/pagos/{id}` | Consulta un pago. |

## Ejecutar en DEV

```powershell
cd services/ingresos-pagos-ms
docker compose -f compose-dev.yml up -d
.\mvnw.cmd spring-boot:run
```

Swagger: `http://localhost:8082/swagger-ui.html`. Health: `http://localhost:8082/actuator/health`.

Segunda instancia, en otra terminal (puerto fijo, no aleatorio):

```powershell
cd services/ingresos-pagos-ms
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"
```

Requiere `config`, `eureka` y `gateway` arriba (ver [`../../infra/README.md`](../../infra/README.md)), y Kafka arriba (ver [`../../kafka/README.md`](../../kafka/README.md)) para consumir/publicar eventos.

## Base de datos

`ingresos_pagos_db` en el puerto `15433` (DEV) / `25433` (PROD local).
