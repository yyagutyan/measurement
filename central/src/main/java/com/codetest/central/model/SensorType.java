package com.codetest.central.model;

public enum SensorType {
    Temperature("t1"),
    Humidity("h1");

    private String value;

    SensorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SensorType fromString(String value) {
        for (SensorType sensorType : SensorType.values()) {
            if (sensorType.getValue().equalsIgnoreCase(value)) {
                return sensorType;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found");
    }
}