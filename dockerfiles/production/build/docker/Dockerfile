#
# Build stage
#
FROM eclipse-temurin:17.0.7_7-jdk-jammy AS builder

# Downloading and installing Maven
# 1- Define a constant with the version of maven you want to install
ARG MAVEN_VERSION=3.9.5
# 2- Define a constant with the working directory
ARG USER_HOME_DIR="/root"
# 3- Define the URL where maven can be downloaded from
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
# 4- Create the directories, download maven, install it, remove downloaded file and set links
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && echo "Downloading maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  \
  && echo "Unziping maven" \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# 5- Define environmental variables required by Maven, like Maven_Home directory and where the maven repo is located
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
RUN mvn -v | grep ${MAVEN_VERSION}
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml test
RUN mvn -f /home/app/pom.xml -Dmaven.test.skip=true clean package

#
# Package stage
#
FROM eclipse-temurin:17.0.7_7-jre-jammy AS packager
# Update and upgrade
RUN apt-get update -y && apt-get upgrade -y
COPY dockerfiles/production/build/docker/entrypoint.sh /etc/entrypoint.sh
RUN chmod +x /etc/entrypoint.sh
COPY --from=builder /home/app/target/*.jar /usr/local/lib/
CMD ["/etc/entrypoint.sh"]