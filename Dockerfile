FROM node:14 AS node-build
WORKDIR /usr/src/app
COPY ui /usr/src/app
ENV VUE_APP_SERVER_URL=/api
RUN npm install && npm run build

FROM maven:3-adoptopenjdk-14 AS mvn-build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
COPY --from=node-build /usr/src/app/dist /usr/src/app/ui/dist
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM adoptopenjdk/openjdk14:alpine-jre
VOLUME /tmp
COPY --from=mvn-build /usr/src/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
