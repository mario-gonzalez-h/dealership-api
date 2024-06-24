package com.example.dealership_api.controller;

import com.example.dealership_api.dao.VehicleDao;
import com.example.dealership_api.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealership/vehicles")
public class VehiclesController {

    private final VehicleDao vehicleDao;

    @Autowired
    public VehiclesController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getVehicles(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer minMiles,
            @RequestParam(required = false) Integer maxMiles,
            @RequestParam(required = false) String type) {
        List<Vehicle> vehicles = vehicleDao.searchVehicles(minPrice, maxPrice, make, model, minYear, maxYear, color, minMiles, maxMiles, type);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/{vin}")
    public ResponseEntity<Vehicle> getVehicleByVIN(@PathVariable int vin) {
        Vehicle vehicle = vehicleDao.findVehicleByVIN(vin);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addVehicle(@RequestBody Vehicle vehicle) {
        vehicleDao.addVehicle(vehicle);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{vin}")
    public ResponseEntity<Void> updateVehicle(@PathVariable int vin, @RequestBody Vehicle vehicle) {
        vehicle.setVin(vin);
        vehicleDao.updateVehicle(vehicle);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{vin}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable int vin) {
        vehicleDao.deleteVehicle(vin);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
