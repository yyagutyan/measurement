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
public class HumidityKafkaConsumerIntegrationTest {
    @Autowired
    private KafkaTemplate<String, Measurement> kafkaTemplate;

    @MockBean
    private SensorAlertService sensorAlertService;

    @Value("${sensor.humidity.topic.name}")
    private String humidityTopic;

    @Test
    void givenHumidityMeasurement_whenThresholdExceeded_thenAlertIsTriggered() throws InterruptedException {
        Measurement highHumidityMeasurement = new Measurement("h1", 70.0, SensorType.Humidity);

        kafkaTemplate.send(new ProducerRecord<>(humidityTopic, highHumidityMeasurement));
        Thread.sleep(500);

        await().atMost(1, SECONDS).untilAsserted(() ->
                verify(sensorAlertService, times(1)).alert(highHumidityMeasurement)
        );
    }

    @Test
    void givenHumidityMeasurement_whenThresholdNotExceeded_thenNoAlertIsTriggered() {
        Measurement normalHumidityMeasurement = new Measurement("h1", 49.0, SensorType.Humidity);

        kafkaTemplate.send(new ProducerRecord<>(humidityTopic, normalHumidityMeasurement));

        await().atMost(5, SECONDS).untilAsserted(() ->
                verify(sensorAlertService, never()).alert(normalHumidityMeasurement)
        );
    }
}
