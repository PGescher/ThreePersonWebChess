FROM openjdk:21-jdk
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/springboot-0.0.1.jar ovguccd.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar ovguccd.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar ovguccd.jar
