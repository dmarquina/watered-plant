package com.dmarquina.wateredplant.service.impl;

import com.dmarquina.wateredplant.model.Plant;
import com.dmarquina.wateredplant.repository.PlantRepository;
import com.dmarquina.wateredplant.service.PlantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("plantService")
@Transactional(readOnly = true)
public class PlantServiceImpl implements PlantService {

  @Autowired
  private PlantRepository plantRepository;

  @Override
  @Transactional
  public Plant create(Plant plant) {
      return plantRepository.save(plant);
  }
}
