package com.rosendo.register_car.Domain.repositories;

import com.rosendo.register_car.Domain.models.UserCarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserCarRepository extends JpaRepository<UserCarModel, Long> { }
