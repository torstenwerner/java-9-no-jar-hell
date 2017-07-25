FROM openjdk:9
MAINTAINER Torsten Werner

# https://github.com/docker-library/openjdk/issues/101
RUN bash -c '([[ ! -d $JAVA_SECURITY_DIR ]] && ln -s $JAVA_HOME/lib $JAVA_HOME/conf) || (echo "Found java conf dir, package has been fixed, remove this hack"; exit -1)'

WORKDIR /usr/src/app

# download the gradle wrapper
COPY gradlew /usr/src/app/
COPY gradle /usr/src/app/gradle/
RUN ./gradlew --version

# build the project and run tests
COPY . /usr/src/app/
RUN ./gradlew build

CMD ["./gradlew", "run"]
