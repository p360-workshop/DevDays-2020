


# Modern ETL pipeline - Kafka Workshop

## Purpose

To understand basics of Kafka working & showcase a simple usecase of realtime  streaming  data aggregation with Kafka Streams application.

### Overview

The application in demo is a simple word count application in streaming fashion, where it comprises of four main components:

  - A Producer application which would create the streaming word data to send it out to Kafka topics.
  - A Kafka Server to store various data generated from source, intermediate processing data & output data.
  - A Consumer application responsible for consuming generated aggregated output data for further data consumption.
  - A Kafka Streams application which would consume the input streams data and do runtime aggregation to generated aggregated output in another output kafka topic.

### Codebase

The Kafka Streaming application codebase is available [here](https://github.com/confluentinc/kafka-streams-examples/blob/6.0.0-post/src/main/java/io/confluent/examples/streams/WordCountLambdaExample.java).

## Steps to Perform:

### Creating & executing Kafka Docker image

  - Login to your vm (via [gucamole login](https://hue.providerdataplatform.net/guacamole/)) using your username & password.
  - Execute below commands to download codebase repo to create & execute Kafka docker image (Please make sure to replace <msid> token)
    ```sh
    $ sudo su
    $ curl -L -O https://github.com/p360-workshop/DevDays-2020/archive/master.zip
    $ unzip master.zip
    $ cd DevDays-2020-master/kafka
    $ docker build . -t <msid>_dd2020_p360kafka
    $ docker run <msid>_dd2020_p360kafka:latest
    ```
### Setting up prerequisite kafka topics for execution

 - Kafka Streaming application would internally require input/optput & internal topics to perform realtime streaming aggregation.
 - In Another brower terminal, Login to your vm (via [gucamole login](https://hue.providerdataplatform.net/guacamole/)) using your username & password.
 - Execute below commands to setup Kafka topics (Please make sure to replace <msid> token)
    ```sh
    $ sudo su
    To get running <container_id> (keep it handy for all further executions).
    $ docker ps
    To go to terminal of running <container_id>:
    $ docker exec -it <container_id> /bin/bash
    $ ./kafka-topics --create --topic streams-plaintext-input --zookeeper localhost:2181 --partitions 1 --replication-factor 1
    $ ./kafka-topics --create --topic streams-wordcount-output --zookeeper localhost:2181 --partitions 1 --replication-factor 1
    $ ./kafka-topics --create --topic  wordcount-lambda-example-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition  --zookeeper localhost:2181 --partitions 1 --replication-factor 1
    $ ./kafka-topics --create --topic  wordcount-lambda-example-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog   --zookeeper localhost:2181  --partitions 1 --replication-factor 1
    ```
### Running Kafka Consumer to validate the aggregation output

 - In real word the consumer itself could be an application which would consume the data to get required business processing but in order to showcase in demo purpose, kafka console consumer would be used to check generated output
 - In the same browser terminal used to setup Kafka topics, execute below command which would show a terminal with generated output
    ```sh
    $ ./kafka-console-consumer --topic streams-wordcount-output --from-beginning --bootstrap-server localhost:9092  --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
    ```

### Running Kafka Streaming application to perform real time aggregation & store data into output kafka topic

 - The streaming application jar has been embeded into the docker image that was build in earlier steps.
 - In Another brower terminal, Login to your vm (via [gucamole login](https://hue.providerdataplatform.net/guacamole/)) using your username & password.
 - Execute below commands to run the streaming applications which will facilitate to generate output
    ```sh
    $ sudo su
    $ docker exec -it <container_id> /bin/bash
    $ java -cp kafka-streams-examples-6.0.0-standalone.jar io.confluent.examples.streams.WordCountLambdaExample
    ```

### Running Kafka Producer application to generate real time data to valdiate the application

 - In real word the producer itself could be an application which would generate the data to but in order to showcase in demo purpose, kafka console producer would be used to generate sample words
 - In Another brower terminal, Login to your vm (via [gucamole login](https://hue.providerdataplatform.net/guacamole/)) using your username & password.
 - Execute below commands to run the streaming applications which will facilitate to generate output
    ```sh
    $ sudo su
    $ docker exec -it <container_id> /bin/bash
    $ ./kafka-console-producer --broker-list localhost:9092 --topic streams-plaintext-input
    ```
### Valdiate Application

 - The data that woudl be produced over producer terminal would be aggregated based on words and the resultatnt aggregated output would be displayed with its count over the consumer terminal.
 
### Cleanup resources

 - Being a good application developer, it necessary to use resource optimially, its better to free up resources after its d=being used.
 - Please execute below command to clean up not required docker containers:
    ```sh
    $ sudo su
    $ docker kill <container_id>
    ``` 


## Future Improvements

 - Considering Optum scenario, the producer application can bevaried from CAEs/EMR or any large application generating data and publishing to Kafka topics.
 - The streaming application would be responsible to perform required business aggregations to get the output.
 - The consumers of the output could be near real time dashboards built over various storage systems.
 
