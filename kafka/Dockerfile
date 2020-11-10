FROM openjdk:8-jdk

# install net-tools and vim
RUN apt update \
    && apt install net-tools -y \
    && apt install vim -y

# Download & install confluent kafka
RUN cd /opt \
    && curl -O http://packages.confluent.io/archive/5.2/confluent-5.2.4-2.12.tar.gz \
    && tar xzf ./confluent-5.2.4-2.12.tar.gz \
    && rm -f ./confluent-5.2.4-2.12.tar.gz \
    && mv ./confluent-5.2.4/ /opt/confluent-kafka

# Export environment variables
ENV KAFKA_HOME=/opt/confluent-kafka

WORKDIR /opt/confluent-kafka/bin/

COPY run.sh run.sh
COPY target/kafka-streams-examples-6.0.0-standalone.jar kafka-streams-examples-6.0.0-standalone.jar
RUN chmod 777 /opt/confluent-kafka/
RUN chmod 777 run.sh
ENTRYPOINT [ "sh","run.sh" ]
