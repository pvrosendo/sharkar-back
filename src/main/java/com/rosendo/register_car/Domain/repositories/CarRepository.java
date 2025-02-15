package com.rosendo.register_car.Domain.repositories;

import com.rosendo.register_car.Domain.models.CarModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<CarModels, Long> { }
