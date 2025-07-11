package com.thederailingmafia.carwash.carservice.service;

import com.thederailingmafia.carwash.carservice.dto.CarRequest;
import com.thederailingmafia.carwash.carservice.exception.CarNotFoundException;
import com.thederailingmafia.carwash.carservice.exception.UserNotFoundException;
import com.thederailingmafia.carwash.carservice.model.Car;
import com.thederailingmafia.carwash.carservice.model.Customer;
import com.thederailingmafia.carwash.carservice.repository.CarRepository;
import com.thederailingmafia.carwash.carservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Car addCar(CarRequest carRequest,String userEmail) throws UserNotFoundException {
        Car car = new Car();
        car.setUserEmail(userEmail);
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setLicenseNumberPlate(carRequest.getLicenseNumberPlate());

        Customer customer = customerRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Customer not found"));

        car.setCustomer(customer);

        return carRepository.save(car);
    }

    public Car getCar(Long carId, String userEmail) throws CarNotFoundException {
        Car car = carRepository.findById(carId)
                .filter(c -> c.getUserEmail().equals(userEmail))
                .orElseThrow(() -> new CarNotFoundException("Car not Found or access denied"));
        return car;
    }

    public List<Car> getAllCars(String email) throws UserNotFoundException {
       List<Car> cars = carRepository.findByUserEmail(email);
       if(cars.isEmpty()){
           throw new UserNotFoundException("User not found");
       }
       return cars;
    }

    public Car updateCar(Long id, CarRequest request, String userEmail) throws UserNotFoundException {
        Car car = carRepository.findById(id)
                .filter(c -> c.getUserEmail().equals(userEmail))
                .orElseThrow(() -> new UserNotFoundException("Car not found or access denied"));
        car.setModel(request.getModel());
        car.setLicenseNumberPlate(request.getLicenseNumberPlate());
        car.setBrand(request.getBrand());
        Car updatedCar = carRepository.save(car);
        return updatedCar;
    }

}
