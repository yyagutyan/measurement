package com.codetest.central.service;

import com.codetest.central.model.Measurement;
import com.codetest.central.model.SensorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class StandardMeasurementServiceTest {

    @Mock
    private SensorAlertService alertService;

    @Mock
    private MeasurementValidatorFactory validatorFactory;

    @Mock
    private MeasurementValidator validator;

    @InjectMocks
    private StandardMeasurementService service;

    @Test
    void testProcessMeasurement_ThresholdExceeded() {
        Measurement measurement = new Measurement("h1", 30, SensorType.Humidity); // Initialize with appropriate values

        when(validatorFactory.getValidator(measurement.sensorType())).thenReturn(validator);
        when(validator.isThresholdExceeded(measurement)).thenReturn(true);

        service.processMeasurement(measurement);

        verify(alertService, times(1)).alert(measurement);
    }

    @Test
    void testProcessMeasurement_ThresholdNotExceeded() {
        Measurement measurement = new Measurement("t1", 30, SensorType.Temperature); // Initialize with appropriate values

        when(validatorFactory.getValidator(measurement.sensorType())).thenReturn(validator);
        when(validator.isThresholdExceeded(measurement)).thenReturn(false);

        service.processMeasurement(measurement);

        verify(alertService, times(0)).alert(measurement);
    }
}
