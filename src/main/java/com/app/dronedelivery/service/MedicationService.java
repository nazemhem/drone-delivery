package com.app.dronedelivery.service;

import com.app.dronedelivery.entity.Medication;
import com.app.dronedelivery.repos.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public ResponseEntity<? extends Object> createMedication(Medication medication) {
        return ResponseEntity.status(HttpStatus.OK).body(medicationRepository.save(medication));
    }
}
