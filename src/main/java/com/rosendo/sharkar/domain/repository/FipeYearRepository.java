package com.rosendo.sharkar.domain.repository;

import com.rosendo.sharkar.domain.model.FipeYearModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FipeYearRepository extends JpaRepository<FipeYearModels, Integer> {
    List<FipeYearModels> getByModelId(Integer modelId);
    Integer findYearIdByYearValue(String yearValue);
}
