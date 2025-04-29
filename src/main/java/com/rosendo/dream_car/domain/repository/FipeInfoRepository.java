package com.rosendo.dream_car.domain.repository;

import com.rosendo.dream_car.domain.model.FipeInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FipeInfoRepository extends JpaRepository<FipeInfoModel, Integer> { }
