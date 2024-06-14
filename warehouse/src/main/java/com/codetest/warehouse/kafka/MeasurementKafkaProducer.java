package com.codetest.warehouse.kafka;

import com.codetest.warehouse.model.Measurement;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Component
public class MeasurementKafkaProducer {

    @Autowired
    private KafkaSender<String, Measurement> kafkaSender;

    public Mono<Void> send(Measurement measurement, String topic) {
        return kafkaSender.send(Mono.just(SenderRecord.create(new ProducerRecord<>(topic, measurement), null)))
                .then();
    }
}
