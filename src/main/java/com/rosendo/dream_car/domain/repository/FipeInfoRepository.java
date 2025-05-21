package com.rosendo.dream_car.domain.repository;

import com.rosendo.dream_car.domain.model.FipeInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FipeInfoRepository extends JpaRepository<FipeInfoModel, Long> {

    @Query("SELECT u FROM FipeInfoModel u WHERE u.modelId =:modelId and u.brandId =:brandId and u.modelYear =:modelYear ")
    FipeInfoModel findByModelIdAndBrandIdAndModelYear(
            Integer modelId,
            Integer brandId,
            String modelYear);
}
