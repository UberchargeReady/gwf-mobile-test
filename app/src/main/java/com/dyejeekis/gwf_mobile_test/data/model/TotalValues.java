package com.dyejeekis.gwf_mobile_test.data.model;

public class TotalValues extends Entity {

    private final int meters, errors, readouts;
    private final float volume;

    public TotalValues(int meters, float volume, int errors, int readouts) {
        this.meters = meters;
        this.errors = errors;
        this.readouts = readouts;
        this.volume = volume;
    }

    public int getMeters() {
        return meters;
    }

    public int getErrors() {
        return errors;
    }

    public int getReadouts() {
        return readouts;
    }

    public float getVolume() {
        return volume;
    }
}
