package com.rosendo.register_car.Domain.repositories;

import com.rosendo.register_car.Domain.models.CarModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelsRepository extends JpaRepository<CarModels, Long> {
    List<CarModels> getByBrandId(Long brandId);
}
