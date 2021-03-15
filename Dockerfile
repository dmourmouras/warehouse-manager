FROM maven:3.6.3-jdk-11-slim as build

WORKDIR /workspace/app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src/ src/

RUN mvn package

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM adoptopenjdk/openjdk11:ubi

ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","com.dm.WarehouseManagerApplication"]

