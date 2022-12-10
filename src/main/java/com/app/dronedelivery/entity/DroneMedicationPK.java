package com.app.dronedelivery.entity;

import java.io.Serializable;

public class DroneMedicationPK implements Serializable {


    private Drone drone;

    private Medication medication;

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
