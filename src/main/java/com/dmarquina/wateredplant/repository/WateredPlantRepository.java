package com.dmarquina.wateredplant.repository;

import com.dmarquina.wateredplant.model.WateredPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface WateredPlantRepository extends JpaRepository<WateredPlant, Long> {
  List<WateredPlant> findAllByOrderByIdDesc();
}
