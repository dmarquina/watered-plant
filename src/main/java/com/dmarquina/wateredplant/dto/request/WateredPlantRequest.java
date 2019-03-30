package com.dmarquina.wateredplant.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class WateredPlantRequest {
  private PlantRequest plant;
  private Long minWateringDays;
  private Long maxWateringDays;
  private LocalDate lastDayWatering;
}
