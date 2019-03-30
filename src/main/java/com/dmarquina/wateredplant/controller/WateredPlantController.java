package com.dmarquina.wateredplant.controller;

import com.dmarquina.wateredplant.dto.request.UpdateLastDayWateringRequest;
import com.dmarquina.wateredplant.dto.request.WateredPlantRequest;
import com.dmarquina.wateredplant.dto.response.PlantResponse;
import com.dmarquina.wateredplant.dto.response.WateredPlantResponse;
import com.dmarquina.wateredplant.model.Plant;
import com.dmarquina.wateredplant.model.WateredPlant;
import com.dmarquina.wateredplant.service.WateredPlantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Plantas Regadas")
@RestController
@RequestMapping("/wateredplant")
public class WateredPlantController {

  @Autowired
  WateredPlantService wateredPlantService;

  @ApiOperation(value = "Listar plantas regadas",
      notes = "Servicio para listar las plantas regadas")
  @ApiResponses(
      value = { @ApiResponse(code = 201, message = "Plantas regadas listadas correctamente"),
          @ApiResponse(code = 400, message = "Solicitud inválida"),
          @ApiResponse(code = 500, message = "Error en el servidor") })
  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<WateredPlantResponse> findAllWateredPlants() {
    return wateredPlantService.findAll()
        .stream()
        .map(this::convertWateredPlantToWateredPlantResponse)
        .collect(Collectors.toList());
  }

  private WateredPlantResponse convertWateredPlantToWateredPlantResponse(
      WateredPlant wateredPlant) {
    WateredPlantResponse wpr = new WateredPlantResponse();

    BeanUtils.copyProperties(wateredPlant, wpr);
    wpr.setDaysSinceLastDayWatering(getDaysSinceLastWatering(wateredPlant));

    PlantResponse pr = new PlantResponse();
    BeanUtils.copyProperties(wateredPlant.getPlant(), pr);
    wpr.setPlant(pr);
    return wpr;
  }

  @ApiOperation(value = "Crear planta regada",
      notes = "Servicio para crear una nueva planta regada")
  @ApiResponses(value = { @ApiResponse(code = 201, message = "Planta regada creada correctamente"),
      @ApiResponse(code = 400, message = "Solicitud inválida"),
      @ApiResponse(code = 500, message = "Error en el servidor") })
  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public WateredPlantResponse createWateredPlant(
      @RequestBody WateredPlantRequest wateredPlantRequest) {

    WateredPlant newWateredPlant = new WateredPlant();
    BeanUtils.copyProperties(wateredPlantRequest, newWateredPlant);
    Plant plant = new Plant();
    BeanUtils.copyProperties(wateredPlantRequest.getPlant(), plant);
    newWateredPlant.setPlant(plant);

    WateredPlant wateredPlantCreated = wateredPlantService.create(newWateredPlant);

    WateredPlantResponse wateredPlantResponse = new WateredPlantResponse();
    BeanUtils.copyProperties(wateredPlantCreated, wateredPlantResponse);
    wateredPlantResponse.setDaysSinceLastDayWatering(getDaysSinceLastWatering(wateredPlantCreated));

    PlantResponse prr = new PlantResponse();
    BeanUtils.copyProperties(wateredPlantCreated.getPlant(), prr);
    wateredPlantResponse.setPlant(prr);
    return wateredPlantResponse;
  }

  @ApiOperation(value = "Actualizar último día de riego",
      notes = "Servicio para Actualizar último día de riego")
  @ApiResponses(value = { @ApiResponse(code = 201, message = "Planta actualizada correctamente"),
      @ApiResponse(code = 400, message = "Solicitud inválida"),
      @ApiResponse(code = 500, message = "Error en el servidor") })
  @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public WateredPlantResponse waterPlant(@PathVariable Long id) {

    WateredPlant wateredPlantPatched =
        wateredPlantService.updateLastDayWatering(id, LocalDate.now());

    WateredPlantResponse wateredPlantResponse = new WateredPlantResponse();

    BeanUtils.copyProperties(wateredPlantPatched, wateredPlantResponse);
    wateredPlantResponse.setDaysSinceLastDayWatering(getDaysSinceLastWatering(wateredPlantPatched));

    PlantResponse pr = new PlantResponse();
    BeanUtils.copyProperties(wateredPlantPatched.getPlant(), pr);
    wateredPlantResponse.setPlant(pr);

    return wateredPlantResponse;
  }

  private Long getDaysSinceLastWatering(WateredPlant wateredPlant) {
    return wateredPlant.getLastDayWatering()
        .until(LocalDate.now(), ChronoUnit.DAYS);
  }
}
