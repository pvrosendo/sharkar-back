package com.rosendo.register_car.Domain.repositories;

import com.rosendo.register_car.Domain.models.BrandModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandModels, Long> { }
