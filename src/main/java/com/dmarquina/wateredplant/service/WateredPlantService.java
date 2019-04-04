package com.dmarquina.wateredplant.service;

import com.dmarquina.wateredplant.model.WateredPlant;

import java.time.LocalDate;
import java.util.List;

public interface WateredPlantService {

  List<WateredPlant> findAll();

  WateredPlant findById(Long id);

  WateredPlant create(WateredPlant wateredPlant);

  WateredPlant update(WateredPlant wateredPlant);

  WateredPlant updateLastDayWatering(Long id, LocalDate lastDayWatering);
}
