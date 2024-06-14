package com.codetest.central.kafka;

import com.codetest.central.model.Measurement;
import com.codetest.central.service.StandardMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TemperatureKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TemperatureKafkaConsumer.class);

    private final StandardMeasurementService measurementService;

    public TemperatureKafkaConsumer(StandardMeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @KafkaListener(topics = "${sensor.humidity.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(Measurement measurement) {
        try {
            measurementService.processMeasurement(measurement);
        } catch (Exception e) {
            logger.error("Error processing measurement: " + measurement, e);
        }
    }
}
