package com.dyejeekis.gwf_mobile_test.data.model;

public class Meter extends Entity {

    private final float latitude, longitude, volume;
    private final String name, id, type, lastEntry;
    private final MeterState state;

    public Meter(float latitude, float longitude, String name, String id, String type,
                 String lastEntry, float volume, MeterState state) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.volume = volume;
        this.name = name;
        this.id = id;
        this.type = type;
        this.lastEntry = lastEntry;
        this.state = state;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getVolume() {
        return volume;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLastEntry() {
        return lastEntry;
    }

    public MeterState getState() {
        return state;
    }
}
