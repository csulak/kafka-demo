# Kafka consumer and producer using springboot

### Before to start!: Download [kafka](https://www.apache.org/dyn/closer.cgi?path=/kafka/2.8.0/kafka_2.13-2.8.0.tgz) in order to run the next commands

Start zookeeper

```bash
cd <KAFKA_HOME>/kafka_2.12-2.0.0
bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start kafka server

```bash
cd <KAFKA_HOME>/kafka_2.12-2.0.0
bin/kafka-server-start.sh config/server.properties
```


Start a consumer (This is a generic consumer)

```bash
cd <KAFKA_HOME>/kafka_2.12-2.0.0
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic bank-transfers --from-beginning
```


Producer configuration

```yaml
spring:
  kafka:
    producer:
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

Consumer configuration:

```yaml
spring:
  kafka:
    consumer:
      group-id: my-group-id
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: com.example
```

Useful commands:

Create topic

```bash
bin/kafka-topics.sh --zookeeper localhost:2181 --create --replication-factor 1 --partitions 1 --topic bank-transfers
```

List topics
```bash
bin/kafka-topics.sh --zookeeper localhost:2181 --list
```

Delete topic

```bash
bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic bank-transfers
Topic bank-transfers is marked for deletion.
Note: This will have no impact if delete.topic.enable is not set to true.
```

#### This example was copied from [here](https://github.com/altfatterz/kafka-demo/)
#### A basic tutorial of how to install [kafka](https://www.youtube.com/watch?v=qUbxyDTX7y0&ab_channel=ChargeAhead)
#### Good example of [producer](https://www.youtube.com/watch?v=NjHYWEV_E_o&ab_channel=TechPrimers) using bean configurations for Kafka
#### Good example of [consumer](https://www.youtube.com/watch?v=IncG0_XSSBg&ab_channel=TechPrimers) using bean configurations for Kafka