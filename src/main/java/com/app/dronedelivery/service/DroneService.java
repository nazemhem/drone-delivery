package com.app.dronedelivery.service;

import com.app.dronedelivery.entity.Drone;
import com.app.dronedelivery.entity.DroneMedication;
import com.app.dronedelivery.entity.Medication;
import com.app.dronedelivery.entity.enums.STATE;
import com.app.dronedelivery.repos.DroneRepository;
import com.app.dronedelivery.repos.MedicationRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;


    @Autowired
    public DroneService(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    public ResponseEntity<?> getDrones(String state) {
        List<Drone> drones;
        if (state != null) {
            STATE enumState;
            try {
                enumState = STATE.valueOf(state.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid state (" + state + ")");
            }

            drones = droneRepository.findDronesByState(enumState);

        } else
            drones = droneRepository.findAll();
        if (drones.size() == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Drones were found");
        return ResponseEntity.status(HttpStatus.OK).body(drones);
    }

    public ResponseEntity<?> register(Drone droneToRegister) {
        if (droneToRegister == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No drone provided");
        } else if (droneToRegister.getSerialNumber() == null || droneToRegister.getSerialNumber().length() == 0 || droneToRegister.getSerialNumber().length() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Serial number must be provided and must be less than 100 characters");
        } else if (droneToRegister.getModel() == null || Drone.availableModels.stream().noneMatch(s -> s.equals(droneToRegister.getModel()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Model number does not match any of the available models");
        }
        if (droneToRegister.getState() == null) droneToRegister.setState(STATE.IDLE);
        if (droneToRegister.getBatteryCapacity() == 0) droneToRegister.setBatteryCapacity(Drone.BATTERY_DEFAULT);

        Drone d;
        try {
            d = droneRepository.save(droneToRegister);
        } catch (HibernateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(d);
    }

    @Transactional
    public ResponseEntity<?> load(String serialNumber, Map<String, Integer> medsMap) {
        List<Medication> medsList = medicationRepository.findAllById(medsMap.keySet());
        if (medsList.size() == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find medications");
        //check battery level
        Optional<Drone> droneOpt = droneRepository.findById(serialNumber);
        if (!droneOpt.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find drone with serial number: " + serialNumber);
        Drone drone = droneOpt.get();
        if (drone.getBatteryCapacity() < Drone.BATTERY_THRESHOLD)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Drone battery is too low to be loaded " + drone.getBatteryCapacity());

        //start calculating the weight
        double medsWeight = 0;
        Set<DroneMedication> droneMedicationList = new HashSet<>();
        for (Medication med : medsList) {
            medsWeight += med.getWeight() * medsMap.get(med.getCode());
            DroneMedication droneMedication = new DroneMedication();
            droneMedication.setDrone(drone);
            droneMedication.setMedication(med);
            droneMedication.setCount(medsMap.get(med.getCode()));
            droneMedicationList.add(droneMedication);
        }
        if (medsWeight > Drone.WEIGHT_MAX)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medications weight(" + medsWeight + ") exceeds maximum drone weight (" + Drone.WEIGHT_MAX + ")");
        //UNLOAD FIRST
        droneRepository.unloadDrone(drone);
        //LOAD the drone
        drone.setMeds(droneMedicationList);
        drone.setState(STATE.LOADED);
        return ResponseEntity.status(HttpStatus.OK).body(droneRepository.save(drone));
    }

    public ResponseEntity<?> getDrone(String serialNumber, String field) {
        Optional<Drone> droneOptional = droneRepository.findById(serialNumber);
        Drone drone;
        if (!droneOptional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No drone found with this serial number: " + serialNumber);
        drone = droneOptional.get();
        if (field != null) {
            if (field.equalsIgnoreCase("battery")) {
                return ResponseEntity.status(HttpStatus.OK).body(drone.getBatteryCapacity());
            } else if (field.equalsIgnoreCase("meds"))
                return ResponseEntity.status(HttpStatus.OK).body(drone.getMeds());
        }
        return ResponseEntity.status(HttpStatus.OK).body(drone);
    }

    public ResponseEntity<?> updateDrone(String serialNumber, Drone newDrone) {
        Drone drone;
        Optional<Drone> droneOptional = droneRepository.findById(serialNumber);
        if (!droneOptional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No drone found with this serial number: " + serialNumber);
        drone = droneOptional.get();
        if (newDrone.getSerialNumber() != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot update serial number. Delete this and register a new drone.");
        if (newDrone.getBatteryCapacity() != 0)
            drone.setBatteryCapacity(newDrone.getBatteryCapacity());
        if (newDrone.getModel() != null)
            drone.setModel(newDrone.getModel());
        if (newDrone.getState() != null)
            drone.setState(newDrone.getState());
        if (newDrone.getMeds() != null)
            drone.setMeds(newDrone.getMeds());
        System.out.println("Drone after update: " + drone);
        return register(drone);
    }
}
