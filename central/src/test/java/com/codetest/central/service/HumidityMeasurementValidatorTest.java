package com.codetest.central.service;

import com.codetest.central.config.SensorConfig;
import com.codetest.central.model.Measurement;
import com.codetest.central.model.SensorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class HumidityMeasurementValidatorTest {

    @MockBean
    private SensorConfig sensorConfig;

    @Autowired
    private HumidityMeasurementValidator validator;

    @BeforeEach
    void setup() {
        when(sensorConfig.getHumidityThreshold()).thenReturn(60.0);
    }

    @Test
    void testIsThresholdExceeded_Exceeded() {
        Measurement measurement = new Measurement("h1",70.0 , SensorType.Humidity);

        assertTrue(validator.isThresholdExceeded(measurement));
    }

    @Test
    void testIsThresholdExceeded_NotExceeded() {
        Measurement measurement = new Measurement("h1",50.0 , SensorType.Humidity);

        assertFalse(validator.isThresholdExceeded(measurement));
    }
}