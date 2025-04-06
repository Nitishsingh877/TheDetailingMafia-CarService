package com.thederailingmafia.carwash.carservice.dto;

import lombok.Data;

@Data
public class CarRequest {
    private String brand;
    private String model;
    private String licenseNumberPlate;
}
