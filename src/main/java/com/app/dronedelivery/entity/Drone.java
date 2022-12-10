package com.app.dronedelivery.entity;

import com.app.dronedelivery.entity.enums.STATE;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
public class Drone {
    @Id
    @JsonProperty("serial_number")
    @Column(length = SERIAL_NUMBER_MAX_LENGTH)
    private String serialNumber;
    private String model;
    public static final double WEIGHT_MAX = 500;
    public static final int BATTERY_DEFAULT = 50;
    public static final int BATTERY_THRESHOLD = 25;
    private static final int SERIAL_NUMBER_MAX_LENGTH = 100;
    @JsonProperty("battery_capacity")
    private int batteryCapacity;
    @Enumerated(EnumType.STRING)
    private STATE state;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DroneMedication> meds;

    public static final List<String> availableModels;

    static {
        availableModels = (Arrays.asList("Lightweight", "Middleweight", "Cruiserweight", "Heavyweight"));
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public Set<DroneMedication> getMeds() {
        return meds;
    }

    public void setMeds(Set<DroneMedication> meds) {
        this.meds = meds;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "serialNumber='" + serialNumber + '\'' +
                ", model='" + model + '\'' +
                ", batteryCapacity=" + batteryCapacity +
                ", state=" + state +
                ", meds=" + meds +
                '}';
    }
}
