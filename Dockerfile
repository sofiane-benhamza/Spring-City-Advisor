#spring boot needs JDK
FROM openjdk:latest

#specifiy working dir inside image db_city_advisor
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/city_advisor_production.jar .

# Command to run the application when the container starts
CMD ["java", "-jar", "city_advisor_production.jar"]
