# Version française

## Tenisu

API REST pour les joueurs et les statistiques de tennis.

### Tester la version déployée

Les URLs et le secret sont transmis séparément.

Prérequis : `curl` et `jq`.

```bash
KEYCLOAK_URL="https://YOUR-KEYCLOAK-URL"
TENISU_URL="https://YOUR-TENISU-URL"
TENISU_CLIENT_ID="tenisu-api-client"
TENISU_CLIENT_SECRET="SECRET_SENT_PRIVATELY"
```

Vérifier que l’application fonctionne :

```bash
curl "$TENISU_URL/api/v1/actuator/health"
```

Réponse attendue :

```json
{
  "status": "UP"
}
```

Obtenir un token :

```bash
ACCESS_TOKEN="$(curl --silent \
  --request POST \
  "$KEYCLOAK_URL/realms/tenisu/protocol/openid-connect/token" \
  --header "Content-Type: application/x-www-form-urlencoded" \
  --data-urlencode "grant_type=client_credentials" \
  --data-urlencode "client_id=$TENISU_CLIENT_ID" \
  --data-urlencode "client_secret=$TENISU_CLIENT_SECRET" \
  | jq --raw-output '.access_token')"
```

Vérifier que le token a été obtenu :

```bash
test -n "$ACCESS_TOKEN" \
  && test "$ACCESS_TOKEN" != "null" \
  && echo "Token reçu"
```

Tester l’API :

```bash
curl \
  --header "Authorization: Bearer $ACCESS_TOKEN" \
  "$TENISU_URL/api/v1/players"
```

```bash
curl \
  --header "Authorization: Bearer $ACCESS_TOKEN" \
  "$TENISU_URL/api/v1/players/52"
```

```bash
curl \
  --header "Authorization: Bearer $ACCESS_TOKEN" \
  "$TENISU_URL/api/v1/statistics"
```

### Technologies

- Java 21
- Spring Boot
- Spring Security
- OAuth 2.0 / Keycloak
- Maven
- JUnit 5
- Mockito
- Docker

### Exécuter les tests

```bash
./mvnw clean verify
```

### Exécuter l’application en local

```bash
KEYCLOAK_ISSUER_URI="$KEYCLOAK_URL/realms/tenisu" \
KEYCLOAK_JWK_SET_URI="$KEYCLOAK_URL/realms/tenisu/protocol/openid-connect/certs" \
./mvnw spring-boot:run
```

### Endpoints

| Méthode | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/players` | Joueurs classés par rang |
| `GET` | `/api/v1/players/{id}` | Joueur par ID |
| `POST` | `/api/v1/players` | Ajouter un joueur |
| `GET` | `/api/v1/statistics` | Statistiques |
| `GET` | `/api/v1/actuator/health` | État de l’application |

Tous les endpoints métier nécessitent un Bearer token.

Le health check reste public.

### Statistiques initiales

```json
{
  "countryWithHighestWinRatio": "SRB",
  "averageBmi": 23.36,
  "medianHeightCm": 185.0
}
```

### Règles de calcul

- Un rang plus faible correspond à un meilleur joueur.
- Le poids est stocké en grammes.
- La taille est stockée en centimètres.
- `1` représente une victoire.
- `0` représente une défaite.
- Le ratio d’un pays correspond à ses victoires divisées par ses matchs.
- Les joueurs ajoutés sont stockés en mémoire.
- Ils sont perdus au redémarrage de l’application.

### Docker

```bash
docker build -t tenisu:local .
```

```bash
docker run --rm \
  -p 8080:8080 \
  -e KEYCLOAK_ISSUER_URI="$KEYCLOAK_URL/realms/tenisu" \
  -e KEYCLOAK_JWK_SET_URI="$KEYCLOAK_URL/realms/tenisu/protocol/openid-connect/certs" \
  tenisu:local
```

---

# English version

## Tenisu

REST API for tennis players and tennis statistics.

### Test the deployed version

The URLs and client secret are provided separately.

Requirements: `curl` and `jq`.

```bash
KEYCLOAK_URL="https://YOUR-KEYCLOAK-URL"
TENISU_URL="https://YOUR-TENISU-URL"
TENISU_CLIENT_ID="tenisu-api-client"
TENISU_CLIENT_SECRET="SECRET_SENT_PRIVATELY"
```

Check the application:

```bash
curl "$TENISU_URL/api/v1/actuator/health"
```

Expected response:

```json
{
  "status": "UP"
}
```

Request a token:

```bash
ACCESS_TOKEN="$(curl --silent \
  --request POST \
  "$KEYCLOAK_URL/realms/tenisu/protocol/openid-connect/token" \
  --header "Content-Type: application/x-www-form-urlencoded" \
  --data-urlencode "grant_type=client_credentials" \
  --data-urlencode "client_id=$TENISU_CLIENT_ID" \
  --data-urlencode "client_secret=$TENISU_CLIENT_SECRET" \
  | jq --raw-output '.access_token')"
```

Check the token:

```bash
test -n "$ACCESS_TOKEN" \
  && test "$ACCESS_TOKEN" != "null" \
  && echo "Token received"
```

Test the API:

```bash
curl \
  --header "Authorization: Bearer $ACCESS_TOKEN" \
  "$TENISU_URL/api/v1/players"
```

```bash
curl \
  --header "Authorization: Bearer $ACCESS_TOKEN" \
  "$TENISU_URL/api/v1/players/52"
```

```bash
curl \
  --header "Authorization: Bearer $ACCESS_TOKEN" \
  "$TENISU_URL/api/v1/statistics"
```

### Technologies

- Java 21
- Spring Boot
- Spring Security
- OAuth 2.0 / Keycloak
- Maven
- JUnit 5
- Mockito
- Docker

### Run tests

```bash
./mvnw clean verify
```

### Run locally

```bash
KEYCLOAK_ISSUER_URI="$KEYCLOAK_URL/realms/tenisu" \
KEYCLOAK_JWK_SET_URI="$KEYCLOAK_URL/realms/tenisu/protocol/openid-connect/certs" \
./mvnw spring-boot:run
```

### Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/players` | Players ordered by rank |
| `GET` | `/api/v1/players/{id}` | Player by ID |
| `POST` | `/api/v1/players` | Add a player |
| `GET` | `/api/v1/statistics` | Statistics |
| `GET` | `/api/v1/actuator/health` | Application health |

All business endpoints require a Bearer token.

The health check remains public.

### Initial statistics

```json
{
  "countryWithHighestWinRatio": "SRB",
  "averageBmi": 23.36,
  "medianHeightCm": 185.0
}
```

### Calculation rules

- A lower rank means a better player.
- Weight is stored in grams.
- Height is stored in centimetres.
- `1` means a win.
- `0` means a loss.
- A country’s ratio is its wins divided by its matches.
- Added players are stored in memory.
- They disappear when the application restarts.

### Docker

```bash
docker build -t tenisu:local .
```

```bash
docker run --rm \
  -p 8080:8080 \
  -e KEYCLOAK_ISSUER_URI="$KEYCLOAK_URL/realms/tenisu" \
  -e KEYCLOAK_JWK_SET_URI="$KEYCLOAK_URL/realms/tenisu/protocol/openid-connect/certs" \
  tenisu:local
```