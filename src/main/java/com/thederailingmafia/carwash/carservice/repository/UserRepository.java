package com.thederailingmafia.carwash.carservice.repository;



import com.thederailingmafia.carwash.carservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);

//      Optional findByUsername(String username);
}

