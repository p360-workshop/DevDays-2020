steps:-
------------------------------------------------------------------------------------------------------------------
docker build . -t dd2020_p360kafka

docker run dd2020_p360kafka:latest

docker ps
<get container_id>

docker exec -it <container_id> /bin/bash

java -cp kafka-streams-examples-6.0.0-standalone.jar io.confluent.examples.streams.WordCountLambdaExample

****new tab *******

./kafka-topics --create --topic streams-plaintext-input --zookeeper localhost:2181 --partitions 1 --replication-factor 1

./kafka-topics --create --topic streams-wordcount-output --zookeeper localhost:2181 --partitions 1 --replication-factor 1

./kafka-console-consumer --topic streams-wordcount-output --from-beginning --bootstrap-server localhost:9092  --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

****new tab *******

java -cp kafka-streams-examples-6.0.0-standalone.jar io.confluent.examples.streams.WordCountLambdaExample

****new tab *******


****new tab *******
/kafka-console-producer --broker-list localhost:9092 --topic streams-plaintext-input
