package com.app.dronedelivery.repos;

import com.app.dronedelivery.entity.Drone;
import com.app.dronedelivery.entity.enums.STATE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, String> {

    @Query("DELETE FROM DroneMedication d WHERE d.drone = :drone")
    @Modifying
    void unloadDrone(@Param("drone") Drone drone);

    List<Drone> findDronesByState(STATE state);

    @Query("SELECT d.batteryCapacity FROM Drone d WHERE d.serialNumber = :serial_number")
    Integer getDroneBattery(@Param("serial_number") String serialNumber);

}
