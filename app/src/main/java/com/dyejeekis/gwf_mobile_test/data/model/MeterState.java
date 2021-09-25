package com.dyejeekis.gwf_mobile_test.data.model;

public class MeterState {

    private final boolean usWaterLevel, vSensorCommTimeout, waterLevelError, tAirError, tWaterError,
    wAirError, wWaterError, velocityError, systemError, batteryLow, communicationError, parsingError,
    encoderError;

    public MeterState(boolean usWaterLevel, boolean vSensorCommTimeout, boolean waterLevelError,
                      boolean tAirError, boolean tWaterError, boolean wAirError, boolean wWaterError,
                      boolean velocityError, boolean systemError, boolean batteryLow,
                      boolean communicationError, boolean parsingError, boolean encoderError) {
        this.usWaterLevel = usWaterLevel;
        this.vSensorCommTimeout = vSensorCommTimeout;
        this.waterLevelError = waterLevelError;
        this.tAirError = tAirError;
        this.tWaterError = tWaterError;
        this.wAirError = wAirError;
        this.wWaterError = wWaterError;
        this.velocityError = velocityError;
        this.systemError = systemError;
        this.batteryLow = batteryLow;
        this.communicationError = communicationError;
        this.parsingError = parsingError;
        this.encoderError = encoderError;
    }

    public boolean isBatteryLow() {
        return batteryLow;
    }

    public boolean isCommunicationError() {
        return communicationError;
    }

    public boolean isParsingError() {
        return parsingError;
    }

    public boolean isEncoderError() {
        return encoderError;
    }

    public boolean isUsWaterLevel() {
        return usWaterLevel;
    }

    public boolean isvSensorCommTimeout() {
        return vSensorCommTimeout;
    }

    public boolean isWaterLevelError() {
        return waterLevelError;
    }

    public boolean istAirError() {
        return tAirError;
    }

    public boolean istWaterError() {
        return tWaterError;
    }

    public boolean iswAirError() {
        return wAirError;
    }

    public boolean iswWaterError() {
        return wWaterError;
    }

    public boolean isVelocityError() {
        return velocityError;
    }

    public boolean isSystemError() {
        return systemError;
    }
}
