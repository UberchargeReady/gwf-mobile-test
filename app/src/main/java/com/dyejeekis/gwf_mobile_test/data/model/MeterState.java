package com.dyejeekis.gwf_mobile_test.data.model;

public class MeterState {

    private final boolean usWaterLevel, vSensorCommTimeout, waterLevelError, tAirError, tWaterError,
    wAirError, wWaterError, velocityError, SystemError;

    public MeterState(boolean usWaterLevel, boolean vSensorCommTimeout, boolean waterLevelError,
                      boolean tAirError, boolean tWaterError, boolean wAirError, boolean wWaterError,
                      boolean velocityError, boolean systemError) {
        this.usWaterLevel = usWaterLevel;
        this.vSensorCommTimeout = vSensorCommTimeout;
        this.waterLevelError = waterLevelError;
        this.tAirError = tAirError;
        this.tWaterError = tWaterError;
        this.wAirError = wAirError;
        this.wWaterError = wWaterError;
        this.velocityError = velocityError;
        SystemError = systemError;
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
        return SystemError;
    }
}
