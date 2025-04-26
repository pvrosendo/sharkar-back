package com.rosendo.register_car.domain.repository;

import com.rosendo.register_car.domain.model.FipeBrandModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<FipeBrandModels, Integer> {
    FipeBrandModels getByBrandId(Integer id);
}
