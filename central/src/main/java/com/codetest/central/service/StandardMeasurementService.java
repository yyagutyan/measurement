package com.codetest.central.service;

import com.codetest.central.model.Measurement;
import org.springframework.stereotype.Component;

@Component
public class StandardMeasurementService implements MeasurementService  {
    private final SensorAlertService alertService;
    private final MeasurementValidatorFactory validatorFactory;

    public StandardMeasurementService(SensorAlertService alertService, MeasurementValidatorFactory validatorFactory) {
        this.alertService = alertService;
        this.validatorFactory = validatorFactory;
    }

    public void processMeasurement(Measurement measurement) {
        if (validatorFactory.getValidator(measurement.sensorType()).isThresholdExceeded(measurement)) {
            alertService.alert(measurement);
        }
    }
}
