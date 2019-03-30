package com.dmarquina.wateredplant.repository;

import com.dmarquina.wateredplant.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {

}
