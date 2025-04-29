package com.rosendo.dream_car.domain.repository;

import com.rosendo.dream_car.domain.model.FipeCarModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FipeCarRepository extends JpaRepository<FipeCarModels, Integer> {
    List<FipeCarModels> findAllByBrandId(Integer brandId);
}
