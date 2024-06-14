package com.codetest.central.service;

import com.codetest.central.model.Measurement;

public interface MeasurementValidator {
    boolean isThresholdExceeded(Measurement measurement);
}
