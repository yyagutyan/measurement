package com.codetest.warehouse.service;

import com.codetest.warehouse.kafka.MeasurementKafkaProducer;
import com.codetest.warehouse.model.Measurement;
import com.codetest.warehouse.model.SensorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class UdpMessageHandlerService {
    private static final Logger logger = LoggerFactory.getLogger(UdpMessageHandlerService.class);
    private final MeasurementKafkaProducer measurementKafkaProducer;

    public UdpMessageHandlerService(MeasurementKafkaProducer measurementKafkaProducer) {
        this.measurementKafkaProducer = measurementKafkaProducer;
    }

    public void handleReceivedData(byte[] payload, SensorType sensorType, String topic) {
        String data = new String(payload, StandardCharsets.UTF_8);
        Measurement measurement = parseMeasurement(data, sensorType);
        if (measurement != null) {
            measurementKafkaProducer.send(measurement, topic).subscribe();
        }
    }

    private Measurement parseMeasurement(String data, SensorType sensorType) {
        String[] parts = data.split(",");
        if (parts.length != 2) {
            logger.error("Failed to parse Measurement: expected 2 parts but got {}", parts.length);
            return null;
        }

        try {
            String id = parts[0];
            double value = Double.parseDouble(parts[1]);

            return new Measurement(id, value, sensorType);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse Measurement value: '{}' is not a valid double", parts[1], e);
            return null;
        } catch (IllegalArgumentException e) {
            logger.error("Failed to parse Measurement sensor type: '{}'", parts[0], e);
            return null;
        }
    }
}
