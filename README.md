# Reactive-kafka-cassandra

The code contains a spring Webflux REST API integrated with reactor Kafka and reactive repositories.

Installation-
1) Cassandra 9042
2) Kafka 9092

Zookeeper 2181

Run Application in Windows-

1) Run cassandra DB after installation with command - cassandra and cqlsh in seperate cmd windows.
2) Run Zookeeper and kafka in separate cmd windows using below commands-

zookeeper-server-start.bat config\zookeeper.properties
kafka-server-start.bat config\server.properties

3) Create topic app_updates using below command in a separate cmd window.
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic app_updates

4) In cqlsh create database schema and tables required.
  CREATE KEYSPACE bootcamp WITH
  replication={'class':'SimpleStrategy','replication_factor':1};
  CREATE TABLE emp( emp_id int, emp_name text, emp_city text, emp_phone
  text, PRIMARY KEY (emp_id));
  CREATE TABLE emp_skill( emp_id text, java_exp double,spring_exp double,
  PRIMARY KEY ((emp_id,java_exp, spring_exp)));

5) create a .bat file with java -jar EmployeeApp-0.0.1-SNAPSHOT.jar and copy the .bat file in the same directory of the jar.
Double click to run the file.

6) Path for the jar is Reactive-kafka-cassandra/target/EmployeeApp-0.0.1-SNAPSHOT.jar

7) Application will run on localhost:8080 

8) Run kafka consumer console using below command
   kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic app_updates
 
9) Do a POST request on localhost:8080 with URI /createEmployee and format as required and you will be able to see the employee JSON string onto the kafka consumer along with thre entry into the database.
