# obs

Observabilidad de `pagatu` (S3-S4): métricas con Prometheus, logs con
Loki/Promtail, paneles con Grafana. Observa `infra/` y `services/` desde
afuera — no es una dependencia de arranque, el sistema funciona sin esta
carpeta levantada.

## Requisitos

- Docker Desktop corriendo.
- Para ver datos reales: al menos un microservicio corriendo y registrado en
  `eureka` (Prometheus lo descubre solo), y con archivos en su
  carpeta `logs/` (Promtail los lee del disco del host).

## Servicios y versiones (DEV)

| Servicio | Imagen | Puerto |
|---|---|---|
| Prometheus | `prom/prometheus:v3.14.0` | `http://localhost:19090` |
| Loki | `grafana/loki:3.7.6` | `http://localhost:13100` |
| Promtail | `grafana/promtail:3.6.8` | (sin puerto expuesto al host) |
| Grafana | `grafana/grafana:11.4.0` | `http://localhost:13000` (`admin`/`admin`) |

## Uso (DEV)

Con al menos un microservicio ya corriendo (para tener algo que observar):

```powershell
cd obs
docker compose -f compose-dev.yml up -d
docker compose -f compose-dev.yml ps
```

Contenedores esperados:

```text
prometheus-dev
loki-dev
promtail-dev
grafana-dev
```

## Cómo descubre a los microservicios

- **Prometheus** (`prometheus/prometheus-dev.yml`) usa *service discovery*
  contra Eureka (`eureka_sd_configs`, `http://host.docker.internal:18761/eureka`):
  cualquier microservicio que se registre en `eureka` y exponga
  `/actuator/prometheus` aparece solo, sin tocar este archivo. `config`
  y `eureka` están además fijos como *targets* estáticos, porque no se
  registran a sí mismos en Eureka.
- **Promtail** (`promtail/promtail-config-dev.yml`) es distinto: cada
  microservicio es un `job_name` fijo, con su propio volumen montado
  (`../services/<servicio>/logs:/var/log/<servicio>:ro`) en
  `compose-dev.yml`. **No** hay descubrimiento automático — agregar un
  microservicio nuevo requiere una entrada nueva en ambos archivos.

  **Estado actual (S11):** `lms-ms`, `ingresos-pagos-ms` y `auth-ms` están
  cableados en DEV y PROD; en DEV también `gateway` (`infra/gateway/logs`) y
  Keycloak (`keycloak/logs`). Prometheus además raspa las métricas de
  Keycloak (`host.docker.internal:19000/metrics`). Para agregar otro
  servicio: un volumen más en `promtail` (`compose-dev.yml`) y un
  `job_name` más en `promtail-config-dev.yml`, con el mismo patrón que los
  que ya existen.

## Verificar

- Prometheus, pestaña *Targets* (`http://localhost:19090/targets`): los
  microservicios corriendo deben aparecer `UP`.
- Grafana (`http://localhost:13000`, `admin`/`admin`): datasources
  Prometheus y Loki ya provisionados (`grafana/provisioning/datasources/`),
  sin pasos manuales.

## Producción local

`compose.yml` se une a `prod-net` (creada por `infra/compose.yml`),
con los puertos en el rango `2xxxx` (`29090` Prometheus, `23100` Loki). No
incluye Grafana: en PROD local, Prometheus y Loki quedan disponibles para un
Grafana externo o para consultarse directo. Hoy no se levanta: queda lista
para cuando el proyecto despliegue en PROD local.
