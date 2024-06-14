package com.codetest.central.service;

import com.codetest.central.model.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MeasurementValidatorFactory {

    private final Map<SensorType, MeasurementValidator> validators = new HashMap<>();

    @Autowired
    public MeasurementValidatorFactory(TemperatureMeasurementValidator temperatureValidator,
                                       HumidityMeasurementValidator humidityValidator) {
        validators.put(SensorType.Temperature, temperatureValidator);
        validators.put(SensorType.Humidity, humidityValidator);
    }

    public MeasurementValidator getValidator(SensorType sensorType) {
        return validators.get(sensorType);
    }
}
