# keycloak

Servidor de identidad de `hooa` (S11). Reemplaza a `auth-ms` como emisor de tokens: el gateway y los microservicios validan el JWT contra el `issuer-uri` del realm `hooa`.

## Levantar (DEV)

```powershell
cd keycloak
docker compose -f compose-dev.yml up -d
```

| Elemento | Valor |
|---|---|
| Consola de administración | `http://localhost:18180` (usuario `admin`, clave `admin123`, solo DEV) |
| Métricas y health | `http://localhost:19000` (`/metrics`, `/health`) |
| PostgreSQL de Keycloak | `localhost:15436` (`hooa_keycloak` / `hooa`) |
| Logs | `keycloak/logs/` (los lee Promtail) |

## Configurar el realm (una sola vez, a mano)

1. En la consola, **Create realm** → nombre `hooa`.
2. **Realm roles** → crear `CLIENTE` y `ADMIN`. Los nombres deben coincidir con los del gateway (`hasRole("CLIENTE")`, `hasRole("ADMIN")`).
3. **Clients** → **Create client**:
    - Client ID: `hooa-frontend`
    - Client authentication: **Off** (cliente público)
    - Authentication flow: solo **Standard flow**
    - Valid redirect URIs: `http://localhost:4200/auth/callback`
    - Valid post logout redirect URIs: `http://localhost:4200/`
    - Web origins: `http://localhost:4200`
    - Advanced → *Proof Key for Code Exchange Code Challenge Method*: **S256**
4. **Users** → crear `estudiante01` (rol `CLIENTE`) y `admin01` (rol `ADMIN`); en *Credentials* fijar una clave con *Temporary* desactivado.

El `issuer` resultante, `http://localhost:18180/realms/hooa`, es el que está en `infra/config/config-repo/*-dev.yml`.

## Verificar

```powershell
Invoke-RestMethod http://localhost:18180/realms/hooa/.well-known/openid-configuration
```

Debe devolver el documento de descubrimiento con ese `issuer`. Los microservicios no lo consultan al arrancar sino en la primera petición autenticada.

## Qué espera el sistema del token

- `sub`: identifica al estudiante (`idEstudiante`).
- `realm_access.roles`: el gateway lo convierte en `ROLE_<rol>`.
- `preferred_username`: nombre que muestra el frontend.
