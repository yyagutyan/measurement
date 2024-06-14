package com.codetest.central.service;

import com.codetest.central.model.Measurement;
import com.codetest.central.model.SensorType;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"test-temperature", "test-humidity"})
@TestPropertySource(locations = "classpath:application.properties")
class TemperatureKafkaConsumerIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Measurement> kafkaTemplate;

    @MockBean
    private SensorAlertService sensorAlertService;

    @Value("${sensor.temperature.topic.name}")
    private String temperatureTopic;

    @Test
    void givenTemperatureMeasurement_whenThresholdExceeded_thenAlertIsTriggered() throws InterruptedException {
        Measurement highTemperatureMeasurement = new Measurement("t1", 70.0, SensorType.Temperature);

        kafkaTemplate.send(new ProducerRecord<>(temperatureTopic, highTemperatureMeasurement));
        Thread.sleep(500);

        await().atMost(1, SECONDS).untilAsserted(() ->
                verify(sensorAlertService, times(1)).alert(highTemperatureMeasurement)
        );
    }

    @Test
    void givenTemperatureMeasurement_whenThresholdNotExceeded_thenNoAlertIsTriggered() {
        Measurement normalTemperatureMeasurement = new Measurement("t1", 25.0, SensorType.Temperature);

        kafkaTemplate.send(new ProducerRecord<>(temperatureTopic, normalTemperatureMeasurement));

        await().atMost(1, SECONDS).untilAsserted(() ->
                verify(sensorAlertService, never()).alert(normalTemperatureMeasurement)
        );
    }
}