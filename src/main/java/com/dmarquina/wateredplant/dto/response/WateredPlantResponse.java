package com.dmarquina.wateredplant.dto.response;

import lombok.Data;

@Data
public class WateredPlantResponse {
  private Long id;
  private PlantResponse plant;
  private Long minWateringDays;
  private Long maxWateringDays;
  private Long daysSinceLastDayWatering;
}
