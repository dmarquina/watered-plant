package com.dmarquina.wateredplant.service;

import com.dmarquina.wateredplant.model.WateredPlant;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface WateredPlantService {

  List<WateredPlant> findAllMyWateredPlants(String userId);

  WateredPlant findById(Long id);

  WateredPlant create(WateredPlant wateredPlant);

  WateredPlant update(WateredPlant wateredPlant);

  WateredPlant updateLastDayWatering(Long id, LocalDate lastDayWatering);

  WateredPlant updateImagePlant(Long id, MultipartFile newImage);
}
