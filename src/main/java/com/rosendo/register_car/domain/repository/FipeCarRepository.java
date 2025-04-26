package com.rosendo.register_car.domain.repository;

import com.rosendo.register_car.domain.model.FipeCarModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelsRepository extends JpaRepository<FipeCarModels, Integer> {
    List<FipeCarModels> getByBrandId(Integer brandId);
}
