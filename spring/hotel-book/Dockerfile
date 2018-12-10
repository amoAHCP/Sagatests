# First stage: Copy the project and run mvn clean install in Docker to build the project against Java 9
FROM maven:3.6.0-jdk-11 AS mavenbuilder
ADD . /app
WORKDIR /app
RUN mvn clean install

FROM openjdk:12-ea-jdk-alpine3.8 as builder
RUN jlink \
    --add-modules java.sql,java.naming,java.net.http,java.management,java.instrument,java.security.jgss,java.desktop,jdk.unsupported \
    --verbose \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /opt/jre-minimal


FROM alpine:3.8
COPY --from=builder /opt/jre-minimal /opt/jre-minimal
ENV LANG=C.UTF-8 \
    PATH=${PATH}:/opt/jre-minimal/bin
WORKDIR /app
COPY --from=mavenbuilder /app/target/hotel-book-0.0.1-SNAPSHOT.jar ./
ENV JVM_OPTS="-XX:+UseContainerSupport -XX:InitialRAMPercentage=80 -XX:MaxRAMPercentage=95"

CMD java $JVM_OPTS -jar hotel-book-0.0.1-SNAPSHOT.jar