package com.dmarquina.wateredplant.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class WateredPlantResponse {
  private Long id;
  private String name;
  private String image;
  private Long minWateringDays;
  private Long maxWateringDays;
  private Long daysSinceLastDayWatering;
  private LocalDate lastDayWatering;
}
