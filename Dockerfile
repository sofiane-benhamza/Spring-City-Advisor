FROM openjdk:latest

WORKDIR /app

# Copy the cities.csv file to the correct directory
COPY src/main/java/all/code/cities.csv src/main/java/all/code/

# Copy the JAR file from the target directory
COPY target/city_advisor_production.jar .

# Command to run the application when the container starts
CMD ["java", "-jar", "city_advisor_production.jar"]
