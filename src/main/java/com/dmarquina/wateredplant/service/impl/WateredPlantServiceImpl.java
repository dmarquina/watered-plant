package com.dmarquina.wateredplant.service.impl;

import com.dmarquina.wateredplant.model.Plant;
import com.dmarquina.wateredplant.model.WateredPlant;
import com.dmarquina.wateredplant.repository.WateredPlantRepository;
import com.dmarquina.wateredplant.service.PlantService;
import com.dmarquina.wateredplant.service.WateredPlantService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service("wateredPlantService")
public class WateredPlantServiceImpl implements WateredPlantService {

  @Autowired
  private WateredPlantRepository wateredPlantRepository;

  @Autowired
  private PlantService plantService;

  @Override
  public List<WateredPlant> findAll() {
    return wateredPlantRepository.findAll();
  }

  @Override
  @Transactional
  public WateredPlant create(WateredPlant wateredPlant) {
    Plant plant = plantService.create(wateredPlant.getPlant());
    wateredPlant.getPlant()
        .setId(plant.getId());
    return wateredPlantRepository.save(wateredPlant);
  }

  @Override
  @Transactional
  public WateredPlant updateLastDayWatering(Long id, LocalDate lastDayWatering) {
    WateredPlant wateredPlant = wateredPlantRepository.getOne(id);
    wateredPlant.setLastDayWatering(lastDayWatering);
    return wateredPlantRepository.save(wateredPlant);
  }
}
