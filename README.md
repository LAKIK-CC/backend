# LAKIK Backend
## Requirements
- Java 11+ 
- PostgreSQL 14+
- (alternative) Docker Engine
## Development
### Environment Variables
Set `environment variables` with key below (do not use `.env`):
```env
DB_HOST=
DB_NAME=
DB_PASSWORD=
DB_PORT=
DB_USERNAME=
```
### Run the App
```bash
./mvnw spring-boot:run
```
### Compile and Run the JAR
```bash
mvn package -DskipTests
java -jar target/lakik-0.0.1-SNAPSHOT.jar
```
## (Alternative) Development using Docker Compose
### Run the app
```bash
docker-compose up -d
```
and use `docker-compose down` to stop.