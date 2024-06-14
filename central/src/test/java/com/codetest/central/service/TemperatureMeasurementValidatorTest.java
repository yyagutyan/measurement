package com.codetest.central.service;

import com.codetest.central.config.SensorConfig;
import com.codetest.central.model.Measurement;
import com.codetest.central.model.SensorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemperatureMeasurementValidatorTest {

    @Mock
    private SensorConfig sensorConfig;

    @InjectMocks
    private TemperatureMeasurementValidator validator;

    @BeforeEach
    void setup() {
        when(sensorConfig.getTemperatureThreshold()).thenReturn(25.0);
    }

    @Test
    void testIsThresholdExceeded_Exceeded() {
        Measurement measurement = new Measurement("h1", 30, SensorType.Temperature); // Initialize with appropriate values

        assertTrue(validator.isThresholdExceeded(measurement));
    }

    @Test
    void testIsThresholdExceeded_NotExceeded() {
        Measurement measurement = new Measurement("h1", 20.0, SensorType.Temperature); // Initialize with appropriate values

        assertFalse(validator.isThresholdExceeded(measurement));
    }
}
