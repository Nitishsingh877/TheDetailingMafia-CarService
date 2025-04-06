package com.thederailingmafia.carwash.carservice.repository;

import com.thederailingmafia.carwash.carservice.model.Car;
import com.thederailingmafia.carwash.carservice.model.Customer;
import com.thederailingmafia.carwash.carservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    List<Customer> user(UserModel user);

    Optional<Customer> findByEmail(String userEmail);
}
