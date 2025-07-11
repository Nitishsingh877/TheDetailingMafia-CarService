package com.thederailingmafia.carwash.carservice.controller;

import com.thederailingmafia.carwash.carservice.dto.CarRequest;
import com.thederailingmafia.carwash.carservice.exception.CarNotFoundException;
import com.thederailingmafia.carwash.carservice.exception.UserNotFoundException;
import com.thederailingmafia.carwash.carservice.model.Car;
import com.thederailingmafia.carwash.carservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@Tag(description = "car Service",name = "carService")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/health")
    public String health() {
        return "OK";
    }


    @PostMapping("/add")
    @Operation(summary = "Add a new car", description = "add a car")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Car> addCar(@RequestBody CarRequest request) throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        System.out.println(userEmail);
        Car response = carService.addCar(request, userEmail);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary ="Get car Details")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Car> getCar(@PathVariable Long id) throws CarNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        Car response = carService.getCar(id, userEmail);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public List<Car> getAllCars() throws CarNotFoundException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        List<Car> cars = carService.getAllCars(userEmail);
        if(cars.isEmpty()){
            throw new CarNotFoundException("No Cars Found for this User");
        }
        return cars;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update car Details")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody CarRequest request) throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        Car response = carService.updateCar(id, request, userEmail);
        return ResponseEntity.status(200).body(response);
    }



}
