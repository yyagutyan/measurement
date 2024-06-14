package com.codetest.central.service;

import com.codetest.central.model.SensorType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MeasurementValidatorFactoryTest {

    @Mock
    private TemperatureMeasurementValidator temperatureValidator;

    @Mock
    private HumidityMeasurementValidator humidityValidator;

    @InjectMocks
    private MeasurementValidatorFactory factory;

    @Test
    void testGetValidator_Temperature() {
        MeasurementValidator validator = factory.getValidator(SensorType.Temperature);
        assertNotNull(validator);
        assertEquals(temperatureValidator, validator);
    }

    @Test
    void testGetValidator_Humidity() {
        MeasurementValidator validator = factory.getValidator(SensorType.Humidity);
        assertNotNull(validator);
        assertEquals(humidityValidator, validator);
    }
}
