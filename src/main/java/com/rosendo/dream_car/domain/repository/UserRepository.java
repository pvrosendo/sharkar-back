package com.rosendo.dream_car.domain.repository;

import com.rosendo.dream_car.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserName(String username);
    User findUserByEmail(String email);

}
