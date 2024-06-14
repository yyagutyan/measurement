package com.codetest.central.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensorConfig {

    @Value("${sensor.temperature.threshold}")
    private double temperatureThreshold;

    @Value("${sensor.humidity.threshold}")
    private double humidityThreshold;

    public double getTemperatureThreshold() {
        return temperatureThreshold;
    }

    public double getHumidityThreshold() {
        return humidityThreshold;
    }
}
