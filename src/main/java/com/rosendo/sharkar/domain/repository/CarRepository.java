package com.rosendo.sharkar.domain.repository;

import com.rosendo.sharkar.domain.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<CarModel, Long> {
    List<CarModel> findAllByUserId(Long userId);
}
