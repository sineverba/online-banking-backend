#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS builder
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml test
RUN mvn -f /home/app/pom.xml -Dmaven.test.skip=true clean package

#
# Package stage
#
FROM eclipse-temurin:17.0.2_8-jre-focal AS packager
RUN apt-get update -y && apt-get upgrade -y
COPY docker/entrypoint.sh /etc/entrypoint.sh
RUN chmod +x /etc/entrypoint.sh
COPY --from=builder /home/app/target/*.jar /usr/local/lib/
CMD ["/etc/entrypoint.sh"]