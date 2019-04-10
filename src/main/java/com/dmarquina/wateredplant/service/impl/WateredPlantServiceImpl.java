package com.dmarquina.wateredplant.service.impl;

import com.dmarquina.wateredplant.model.WateredPlant;
import com.dmarquina.wateredplant.repository.WateredPlantRepository;
import com.dmarquina.wateredplant.service.AmazonService;
import com.dmarquina.wateredplant.service.WateredPlantService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@Service("wateredPlantService")
public class WateredPlantServiceImpl implements WateredPlantService {

  @Autowired
  private WateredPlantRepository wateredPlantRepository;

  @Autowired
  AmazonService amazonService;

  @Override
  public List<WateredPlant> findAll() {
    return wateredPlantRepository.findAllByOrderByIdDesc();
  }

  @Override
  public WateredPlant findById(Long id) {
    return wateredPlantRepository.findById(id).get();
  }

  @Override
  @Transactional
  public WateredPlant create(WateredPlant wateredPlant) {
    return wateredPlantRepository.save(wateredPlant);
  }

  @Override
  @Transactional
  public WateredPlant update(WateredPlant wateredPlant) {
    return wateredPlantRepository.save(wateredPlant);
  }

  @Override
  @Transactional
  public WateredPlant updateLastDayWatering(Long id, LocalDate lastDayWatering) {
    WateredPlant wateredPlant = wateredPlantRepository.getOne(id);
    wateredPlant.setLastDayWatering(lastDayWatering);
    return wateredPlantRepository.save(wateredPlant);
  }

  @Override
  @Transactional
  public WateredPlant updateImagePlant(Long plantId, MultipartFile newImage) {
    try {
      //TODO: Validar si existe o no
      WateredPlant wateredPlantFound = wateredPlantRepository.findById(plantId).get();
      wateredPlantFound.setImage(amazonService.uploadFile(plantId, newImage));
      return wateredPlantRepository.save(wateredPlantFound);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
