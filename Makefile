#!make
include test.env
export $(shell sed 's/=.*//' test.env)

gradle_command=./gradlew

start: config-dev
    java -jar build/libs/kotlin-auth-service-*-SNAPSHOT.jar

cb:
    $(gradle_command) clean build

fmigrate:
    $(gradle_command) flywayMigrate -i -Pflyway.url=${DATABASE_URL} -Pflyway.user=${DATABASE_USER} -Dflyway.password=${DATABASE_PASSWORD}

fclean:
    $(gradle_command) flywayClean -i -Pflyway.url=${DATABASE_URL} -Pflyway.user=${DATABASE_USER} -Dflyway.password=${DATABASE_PASSWORD}

config-dev:
    export DATABASE_URL=jdbc:postgresql://localhost:5432/postgres
    export VIACEP_URL=https://viacep.com.br/ws/
    export CONTACT_SQS_ADDRESS=http://localhost:9324