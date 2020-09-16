# kotlin-auth-service

[![Build Status](https://travis-ci.org/luizimcpi/kotlin-auth-service.svg?branch=master)](https://travis-ci.org/luizimcpi/kotlin-auth-service)

[![codecov](https://codecov.io/gh/luizimcpi/kotlin-auth-service/branch/master/graph/badge.svg)](https://codecov.io/gh/luizimcpi/kotlin-auth-service)

## SETUP DEV

## SET Environment Variables
```
SERVER_PORT=7000
LOG_LOCATION=/your_user/logs
ENABLE_DEBUG=true
DATABASE_URL=jdbc:postgresql://localhost:5432/postgres
DATABASE_USER=postgres
DATABASE_PASSWORD=postgres
AUTH_SECRET=my_super_secret
VIACEP_URL=https://viacep.com.br/ws/
CONTACT_SQS_ADDRESS=http://localhost:9323
CONTACT_SQS_QUEUE_NAME=sqs-contact
CONTACT_SQS_REGION=us-east-1
```
## Create local sqs using alpine sqs
```
docker run --name alpine-sqs -p 9324:9324 -p 9325:9325 -d roribio16/alpine-sqs

aws --endpoint-url http://localhost:9324 sqs create-queue --queue-name sqs-contact 
```

## Create local postgres database - using docker
```
docker pull postgres

docker run --name teste-postgres -e "POSTGRES_PASSWORD=postgres" -p 5432:5432 -d postgres
```
OR

## Create local postgres database - linux ubuntu or debian
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

## Executing Endpoints
```
File requests.rest can be open with plugin REST client in VSCode
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
- [ ] prevent an user can consult another users information
- [ ] create and configure arch tests ArchUnit
- [ ] pass login crendentials using application/x-www-form-urlencoded
- [ ] create refresh token
- [ ] create paginated contacts CRUD per user