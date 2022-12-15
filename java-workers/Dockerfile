FROM adoptopenjdk/openjdk11

ENV WORKDIR /app
WORKDIR ${WORKDIR}

ARG JAR_FILE
ADD target/${JAR_FILE} ${WORKDIR}/lib/boxpark-java-api.jar

ENTRYPOINT ["java","-jar","/app/lib/boxpark-java-api.jar"]