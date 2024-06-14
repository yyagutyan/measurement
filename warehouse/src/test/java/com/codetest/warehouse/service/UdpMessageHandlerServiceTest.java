package com.codetest.warehouse.service;

import com.codetest.warehouse.kafka.MeasurementKafkaProducer;
import com.codetest.warehouse.model.Measurement;
import com.codetest.warehouse.model.SensorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UdpMessageHandlerServiceTest {

    private MeasurementKafkaProducer measurementKafkaProducer;
    private UdpMessageHandlerService udpMessageHandlerService;

    @BeforeEach
    void setUp() {
        measurementKafkaProducer = mock(MeasurementKafkaProducer.class);
        udpMessageHandlerService = new UdpMessageHandlerService(measurementKafkaProducer);
    }

    @Test
    void handleReceivedData_validPayload_shouldSendMeasurement() {
        byte[] payload = "t1,23.5".getBytes(StandardCharsets.UTF_8);
        SensorType sensorType = SensorType.Temperature;
        String topic = "test-topic";

        when(measurementKafkaProducer.send(any(Measurement.class), eq(topic)))
                .thenReturn(Mono.empty());

        udpMessageHandlerService.handleReceivedData(payload, sensorType, topic);

        ArgumentCaptor<Measurement> measurementCaptor = ArgumentCaptor.forClass(Measurement.class);
        verify(measurementKafkaProducer).send(measurementCaptor.capture(), eq(topic));

        Measurement sentMeasurement = measurementCaptor.getValue();
        assertNotNull(sentMeasurement);
        assertEquals("t1", sentMeasurement.id());
        assertEquals(23.5, sentMeasurement.value());
        assertEquals(SensorType.Temperature, sentMeasurement.sensorType());
    }

    @Test
    void handleReceivedData_invalidPayload_shouldNotSendMeasurement() {
        byte[] payload = "invalid_payload".getBytes(StandardCharsets.UTF_8);
        SensorType sensorType = SensorType.Temperature;
        String topic = "test-topic";

        udpMessageHandlerService.handleReceivedData(payload, sensorType, topic);

        verify(measurementKafkaProducer, never()).send(any(Measurement.class), anyString());
    }
}
