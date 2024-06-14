package com.codetest.warehouse.model;

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
}
