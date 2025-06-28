package com.rosendo.sharkar.domain.repository;

import com.rosendo.sharkar.domain.model.FipeBrandModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FipeBrandRepository extends JpaRepository<FipeBrandModels, Integer> {
    Integer findBrandIdByBrandName(String brandName);
}
