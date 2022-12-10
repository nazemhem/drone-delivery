package com.app.dronedelivery.repos;

import com.app.dronedelivery.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, String> {
}
