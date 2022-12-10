package com.app.dronedelivery.controller;

import com.app.dronedelivery.entity.Drone;
import com.app.dronedelivery.service.DroneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/drone")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping
    public ResponseEntity<?> getDrones(@RequestParam(value = "state", required = false) String state) {
        return droneService.getDrones(state);
    }

    @GetMapping("/{serial_number}")
    public ResponseEntity<?> getDrone(@PathVariable("serial_number") String serialNumber, @RequestParam(value = "field", required = false) String field) {
        return droneService.getDrone(serialNumber, field);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody Drone drone) {
        return droneService.register(drone);
    }

    @PostMapping("/{serial_number}/load")
    public ResponseEntity<?> load(@PathVariable("serial_number") String serialNumber, @RequestBody Map<String, Integer> medications) {
        return droneService.load(serialNumber, medications);
    }

    @PutMapping("/{serial_number}")
    public ResponseEntity<?> updateDrone(@PathVariable("serial_number") String serialNumber, @RequestBody Drone drone) {
        return droneService.updateDrone(serialNumber, drone);
    }

}
