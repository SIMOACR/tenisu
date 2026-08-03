# Version en français

## Tenisu

REST API pour les joueurs de tennis et les statistiques de tennis.

### Technologies

- Java 21
- Spring Boot
- Maven
- JUnit 5
- Mockito
- Docker

### Exécuter les tests

```bash
./mvnw clean verify
```

### Exécuter l'application en local

```bash
./mvnw spring-boot:run
```

### Endpoints

#### Joueurs classés par rang

```http
GET /api/v1/players
```

#### Joueur par ID

```http
GET /api/v1/players/{id}
```

#### Ajouter un joueur

```http
POST /api/v1/players
```

#### Statistiques

```http
GET /api/v1/statistics
```

Statistiques initiales attendues :

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
- Le ratio d'un pays correspond au nombre total de victoires divisé par le nombre total de matchs.
- Les joueurs ajoutés sont stockés en mémoire.
- Les joueurs ajoutés sont perdus au redémarrage de l'application.

### Docker

```bash
docker build -t tenisu:local .
docker run --rm -p 8080:8080 tenisu:local
```

### Health check

```http
GET /actuator/health
```

---

# English version

## Tenisu

REST API for tennis players and tennis statistics.

### Technologies

- Java 21
- Spring Boot
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
./mvnw spring-boot:run
```

### Endpoints

#### Players ordered by rank

```http
GET /api/v1/players
```

#### Player by ID

```http
GET /api/v1/players/{id}
```

#### Add a player

```http
POST /api/v1/players
```

#### Statistics

```http
GET /api/v1/statistics
```

Expected initial statistics:

```json
{
  "countryWithHighestWinRatio": "SRB",
  "averageBmi": 23.36,
  "medianHeightCm": 185.0
}
```

### Calculation rules

- Lower player rank means a better player.
- Weight is stored in grams.
- Height is stored in centimetres.
- `1` means a win.
- `0` means a loss.
- Country ratio is total wins divided by total matches.
- Added players are stored in memory.
- Added players disappear after restart.

### Docker

```bash
docker build -t tenisu:local .
docker run --rm -p 8080:8080 tenisu:local
```

### Health check

```http
GET /actuator/health
```