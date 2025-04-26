package com.rosendo.register_car.domain.repository;

import com.rosendo.register_car.domain.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserCarRepository extends JpaRepository<CarModel, Long> { }
