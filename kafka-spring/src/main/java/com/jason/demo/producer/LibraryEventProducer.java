package com.jason.demo.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.demo.domain.LibraryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class LibraryEventProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public ListenableFuture<SendResult<Integer,String>> sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);


        ListenableFuture<SendResult<Integer,String>> future =  kafkaTemplate.sendDefault(key, value);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }

            @Override
            public void onFailure(Throwable ex) {
                try {
                    handleFailure(key, value, ex);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return future;
    }

    private void handleFailure(Integer key, String value, Throwable ex) throws Throwable {
        log.info(">>> Message send error: \n >>> Key: {} \n >>> Value: {} \n >>> Exception: {}", key, value, ex );
        throw ex;
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String > result) {
        log.info(">>> Message sent successfully: \n >>> Key: {} \n >>> Value: {} \n >>> Result: {}", key, value, result );
    }

}
