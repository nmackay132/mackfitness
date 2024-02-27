# Use a base image with Java and Maven pre-installed
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# copy the Project Object Model file
COPY ./pom.xml ./pom.xml

# fetch all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src

# build for release
RUN mvn package -DskipTests && cp target/mackFitness-0.0.1-SNAPSHOT.jar app.jar

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]