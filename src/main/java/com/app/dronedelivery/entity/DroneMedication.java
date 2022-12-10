package com.app.dronedelivery.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@IdClass(DroneMedicationPK.class)
@Table(name = "drone_medication")
public class DroneMedication {

    @ManyToOne
    @JoinColumn(name = "drone_serial_number")
    @Id
    @JsonBackReference
    private Drone drone;
    @ManyToOne
    @JoinColumn(name = "medication_code")
    @Id
    private Medication medication;

    @Column(name = "med_count")
    private int count;


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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
