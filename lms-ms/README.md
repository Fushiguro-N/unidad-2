# lms-ms

Microservicio de experiencia de aprendizaje. Gestiona el catálogo académico (`Curso`, `Contenido`) y las matrículas de los estudiantes (`Matricula`, `MatriculaDetalle`). Antes de confirmar una matrícula, valida por Feign + Circuit Breaker el estado de cuenta del estudiante en [`ingresos-pagos-ms`](../ingresos-pagos-ms); publica el evento `matricula.creada` y consume `pago.validado` para confirmarla.

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| `GET/POST/PUT/DELETE` | `/api/v1/cursos` | CRUD de cursos. Lectura (`GET`) pública; escritura requiere JWT. |
| `GET/POST/PUT/DELETE` | `/api/v1/contenidos` | CRUD de contenidos (módulos, certificados) asociados a un curso. Lectura pública; escritura requiere JWT. |
| `POST` | `/api/v1/matriculas/enrolar` | Matricula al estudiante autenticado (toma el id del JWT). |
| `GET` | `/api/v1/matriculas` | Lista matrículas. |
| `GET` | `/api/v1/matriculas/{id}` | Consulta una matrícula. |

## Ejecutar en DEV

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

Requiere `config`, `eureka` y `gateway` arriba (ver [`../../infra/README.md`](../../infra/README.md)), y Kafka arriba (ver [`../../kafka/README.md`](../../kafka/README.md)) para publicar/consumir eventos.

## Base de datos

`lms_db` en el puerto `15432` (DEV) / `25432` (PROD local).
