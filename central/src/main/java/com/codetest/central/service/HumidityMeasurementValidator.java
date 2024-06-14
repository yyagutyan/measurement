package com.codetest.central.service;

import com.codetest.central.config.SensorConfig;
import com.codetest.central.model.Measurement;
import com.codetest.central.model.SensorType;
import org.springframework.stereotype.Component;

@Component
public class HumidityMeasurementValidator implements MeasurementValidator {

    private final SensorConfig sensorConfig;

    public HumidityMeasurementValidator(SensorConfig sensorConfig) {
        this.sensorConfig = sensorConfig;
    }

    @Override
    public boolean isThresholdExceeded(Measurement measurement) {
        if (measurement.sensorType() == SensorType.Humidity) {
            return measurement.value() > sensorConfig.getHumidityThreshold();
        }
        return false;
    }
}
