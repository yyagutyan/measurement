package com.codetest.central.kafka;

import com.codetest.central.model.Measurement;
import com.codetest.central.service.StandardMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class HumidityKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(HumidityKafkaConsumer.class);

    private final StandardMeasurementService measurementService;

    public HumidityKafkaConsumer(StandardMeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @KafkaListener(topics = "${sensor.temperature.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(Measurement measurement) {
        try {
            measurementService.processMeasurement(measurement);
        } catch (Exception e) {
            logger.error("Error processing measurement: " + measurement, e);
        }
    }
}
