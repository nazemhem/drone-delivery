package com.app.dronedelivery.controller;

import com.app.dronedelivery.entity.Medication;
import com.app.dronedelivery.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/medication")
public class MedicationController {
    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping
    public ResponseEntity<? extends Object> createMedication(@RequestBody Medication medication) {
        if (medication == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medication body is empty");
        else if (medication.getCode() == null || medication.getCode().length() == 0 || !medication.getCode().matches("[A-Z_0-9]+"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medication code is invalid! Allowed only Upper case letters, numbers, '_'");
        else if (medication.getName() == null || medication.getName().length() == 0 || !medication.getName().matches("[a-zA-Z0-9/-_]+"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medication name is invalid! Allowed only letters, numbers, '-', '_'");
        else if (medication.getWeight() == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medication weight cannot be 0");

        return medicationService.createMedication(medication);
    }
}
