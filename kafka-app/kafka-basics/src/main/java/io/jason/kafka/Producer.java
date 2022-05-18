package io.jason.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Properties;

public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class.getSimpleName());

    public static void main(String[] args) {
        log.info("OK");

        // Create producer props
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        // create a producer record
        String topic = "mytopic";
        String key = "7";
        String value = "Hello, the time is " + new Timestamp(System.currentTimeMillis());

        ProducerRecord<String, String> rec = new ProducerRecord<>(topic, key, value);

        // Send data, is async
        // We are implementing a callback which will run when the results are returned
        producer.send(rec, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    log.info("\n >>> Metadata, topic: " + metadata.topic());
                    log.info("\n >>> Metadata, partition: " + metadata.partition());
                } else {
                    log.info(">>> Exception: " + exception.toString());
                }
            }
        });

        // Flush and close the producer - async. Blocks code until producer records are sent
        producer.flush();
        producer.close(); // also calls flush

    }


}
