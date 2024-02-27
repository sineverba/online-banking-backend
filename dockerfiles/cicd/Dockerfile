#
# Build stage
#
FROM eclipse-temurin:21.0.2_13-jdk-jammy AS builder
# Update and upgrade
RUN apt-get -yq update && \
  apt-get -yqq install ssh
# Downloading and installing Maven
# 1- Define a constant with the version of maven you want to install
ARG MAVEN_VERSION=3.9.6
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
