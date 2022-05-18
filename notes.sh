
# Kafka setup
# ===========

# Installing on Linux. Using Udemy Kafka course
https://slower.udemy.com/course/apache-kafka/

# His code downloads are here
https://www.conduktor.io/kafka-for-beginners

# Instructions here
https://www.conduktor.io/kafka/starting-kafka

# Install Corretto (Amazon Java 11)

# Install Kafka (download and unzip)

# Create symlinks to Kafka folder in home

# Start Zookeeper
zookeeper-server-start.sh ~/kafka/config/zookeeper.properties

# Start Kafka
kafka-server-start.sh ~/kafka/config/server.properties

# CLI producer
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic mytopic

# CLI consumer
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic mytopic --from-beginning

# CLI consumer groups
# Create consumer in group
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic mytopic --group firstgroup
# Describe group's consumers
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group firstgroup


## Kafka programming in Java
# Client libraries
https://www.conduktor.io/kafka/kafka-sdk-list
# Use Gradle with Kafka, fewer errors
# In Intellij, create a new module
# Use that build.gradle
# Go to Google and find Maven repo org.apache.kafka, get kafka-clients lib
# Click on latest version.
# Click on Gradle Short tab, copy entire blob of text
# Paste in build.gradle file in dependencies section in module
# Next find "slf4j api" in Maven repo, do the same
# Do the same with "slf4j simple". These are for logging
# Next pull in the dependencies. Intellij has a little dropdown in the upper right of the text editor
# 


##################################################
### SpringBoot Kafka Project
##################################################

# SpringBoot Initializr, choose Gradle
# Add dependencies Spring Web (since API), Spring for Apache Kafka and Lombok
https://start.spring.io/

# Extract zip file with Spring Project. Open with Intellij and allow Gradle to build
# In Intellij, enable Annotation Processors in settings

# Sending messages in Spring, section 4.1.3
https://docs.spring.io/spring-kafka/reference/html/

# The KafkaTemplate.send() method batches messages before sending to limit the number of connections
# KafkaTemplate's auto-confirguration is done with application.yml and is passed to the producer

# Curl event for POSTing to Spring API
curl -i -d '{"libraryEventId":null,"book":{"bookId":2,"bookName":"Kafka is great","bookAuthor":"Jason P"}}' -H "Content-Type: application/json" -X POST http://localhost:8080/v1/libraryevent


######################
## Tests
######################
# 
# 1. Unit tests
# 2. Integration tests
# 3. End to end tests
# 
# 1. Junit
# 2. Spock
# 
# Integration Tests
# 1. Combines independent layers and tests they work as expected
# 2. 
# 
# 
# CICD Pipeline
# 1. Use embedded Kafka, in memory
# 2. 
# 
# 
# Unit Tests
# 1. Unit tests are handy to mock external dependencies
# 2. Unit tests are faster than integration tests
# 3. Unit tests cover scenarios not possible with integration tests
# 
# 


















