# Code996 Backend Service

Spring Boot backend service for Git repository online analysis.

## Quick Start

```bash
# Install and run
mvn clean install
mvn spring-boot:run
```

Backend will start on: `http://localhost:8080/api`

## Test

```bash
# Health check
curl http://localhost:8080/api/analyze/health

# Analyze repository
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{"gitUrl": "https://github.com/expressjs/express.git", "maxCommits": 1000}'
```

## Build JAR

```bash
mvn clean package
java -jar target/code996-backend-1.0.0.jar
```

## Configuration

Edit `src/main/resources/application.yml` to customize:
- Port
- Temp directory
- Max commits
- Cleanup interval

See parent README.md for full documentation.
