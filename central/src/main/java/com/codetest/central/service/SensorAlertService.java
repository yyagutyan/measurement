package com.codetest.central.service;

import com.codetest.central.model.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SensorAlertService implements AlertService {
    private static final Logger logger = LoggerFactory.getLogger(SensorAlertService.class);

    @Override
    public void alert(Measurement measurement) {
        logger.warn("ALERT: Measurement exceeded threshold - " +
                        "Sensor ID: {}, Value: {}, Sensor Type: {}",
                measurement.id(), measurement.value(), measurement.sensorType());
    }
}
