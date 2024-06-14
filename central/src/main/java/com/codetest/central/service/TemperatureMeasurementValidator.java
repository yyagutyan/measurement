package com.codetest.central.service;


import com.codetest.central.config.SensorConfig;
import com.codetest.central.model.Measurement;
import com.codetest.central.model.SensorType;
import org.springframework.stereotype.Component;

@Component
public class TemperatureMeasurementValidator implements MeasurementValidator {

    private final SensorConfig sensorConfig;

    public TemperatureMeasurementValidator(SensorConfig sensorConfig) {
        this.sensorConfig = sensorConfig;
    }

    @Override
    public boolean isThresholdExceeded(Measurement measurement) {
        if (measurement.sensorType() == SensorType.Temperature) {
            return measurement.value() > sensorConfig.getTemperatureThreshold();
        }
        return false;
    }
}
