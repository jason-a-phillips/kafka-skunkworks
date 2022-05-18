package io.jason.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class Consumer {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class.getSimpleName());

    public static void main(String[] args) {
        log.info("AOK!!!");

        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "secondgroup");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consoomer = new KafkaConsumer<>(props);

        // get a reference to the main thread to add shutdown hook
        final Thread mainThread = Thread.currentThread();
        // add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                log.info("Detected a shutdown.");
                consoomer.wakeup();
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        try {
            // subscribe
            consoomer.subscribe(List.of("mytopic"));

            // poll
            while(true) {
                log.info("Polling....");

                ConsumerRecords<String, String> records = consoomer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    log.info(">>>> Key: " + record.key());
                    log.info(">>>> Value: " + record.value());
                    log.info(">>>> Partition: " + record.partition());
                    log.info(">>>> Offset: " + record.offset());
                }
            }
        } catch (WakeupException e) {
            log.info("Wakeup exception");
        } catch (Exception e) {
            log.error("Unexpected exception!");
        } finally {
            consoomer.close();
            log.info("The consumer is now gracefully closed.");
        }

    }
}
