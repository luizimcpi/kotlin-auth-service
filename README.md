# kotlin-auth-service

## SETUP DEV

## SET Environment Variables
```
SERVER_PORT=7000
LOG_LOCATION=/your_user/logs
ENABLE_DEBUG=true
DATABASE_URL=jdbc:postgresql://localhost:5432/postgres
DATABASE_USER=postgres
DATABASE_PASSWORD=postgres
```

## Create local postgres database - using docker
```
docker pull postgres

docker run --name teste-postgres -e "POSTGRES_PASSWORD=postgres" -p 5432:5432 -d postgres
```
OR

## Create local postgres database - linux
```
sudo apt update
sudo apt install postgresql postgresql-contrib

Change default password:
sudo -u postgres psql
ALTER USER postgres WITH PASSWORD 'postgres';
```

## Flyway Info
```
./gradlew flywayInfo -Dflyway.url=jdbc:postgresql://localhost:5432/postgres -Dflyway.user=postgres -Dflyway.password=postgres
```

## Flyway Migrate
```
./gradlew flywayMigrate -Dflyway.url=jdbc:postgresql://localhost:5432/postgres -Dflyway.user=postgres -Dflyway.password=postgres
```

## Flyway Clean
```
./gradlew flywayClean -Dflyway.url=jdbc:postgresql://localhost:5432/postgres -Dflyway.user=postgres -Dflyway.password=postgres
```

## Start Application using docker
```
1 -> ./gradlew build
2 -> docker build -t kotlin-auth-service .
3 -> docker-compose up
```
OR

## Start Application using gradle
```
1 -> ./gradlew build
2 -> java -jar build/libs/kotlin-auth-service-1.0.0-SNAPSHOT.jar
``` 

## Run Unit Tests
```
./gradlew test

jacocoReport -> build/reports/jacoco/test/jacocoTestReport.xml
```


## Used Libs

[JAVALIN](https://javalin.io/)

[KOIN](https://insert-koin.io/)

[HikariCP](https://github.com/brettwooldridge/HikariCP)

[konfig](https://github.com/npryce/konfig)

[Exposed](https://github.com/JetBrains/Exposed)

[Mockk](https://mockk.io/)

[Junit 5](https://junit.org/junit5/)

## TO DO
- [ ] application health check
- [ ] remove password from login response
- [ ] prevent an user can consult another users information
- [ ] create and configure arch tests ArchUnit