package com.dmarquina.wateredplant.controller;

import com.dmarquina.wateredplant.dto.request.NewWateredPlantRequest;
import com.dmarquina.wateredplant.dto.request.UpdateLastDayWateringRequest;
import com.dmarquina.wateredplant.dto.request.UpdateWateredPlantRequest;
import com.dmarquina.wateredplant.dto.response.WateredPlantResponse;
import com.dmarquina.wateredplant.model.WateredPlant;
import com.dmarquina.wateredplant.service.WateredPlantService;
import com.dmarquina.wateredplant.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    wpr.setDaysSinceLastDayWatering(
        DateUtil.getDaysSinceLastWatering(wateredPlant.getLastDayWatering()));
    return wpr;
  }

  @ApiOperation(value = "Obtiene la planta regada por id",
      notes = "Servicio para obtener la planta regada por id")
  @ApiResponses(
      value = { @ApiResponse(code = 201, message = "Planta regada obtenida correctamente"),
          @ApiResponse(code = 400, message = "Solicitud inválida"),
          @ApiResponse(code = 500, message = "Error en el servidor") })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public WateredPlantResponse wateredPlantById(@PathVariable Long id) {
    WateredPlant wateredPlantFound = wateredPlantService.findById(id);
    WateredPlantResponse wateredPlantResponse = new WateredPlantResponse();
    BeanUtils.copyProperties(wateredPlantFound, wateredPlantResponse);
    wateredPlantResponse.setDaysSinceLastDayWatering(
        DateUtil.getDaysSinceLastWatering(wateredPlantFound.getLastDayWatering()));
    return wateredPlantResponse;
  }

  @ApiOperation(value = "Crear planta regada",
      notes = "Servicio para crear una nueva planta regada")
  @ApiResponses(value = { @ApiResponse(code = 201, message = "Planta regada creada correctamente"),
      @ApiResponse(code = 400, message = "Solicitud inválida"),
      @ApiResponse(code = 500, message = "Error en el servidor") })
  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public WateredPlantResponse createWateredPlant(
      @RequestBody NewWateredPlantRequest wateredPlantRequest) {

    WateredPlant newWateredPlant = new WateredPlant();
    BeanUtils.copyProperties(wateredPlantRequest, newWateredPlant);

    WateredPlant wateredPlantCreated = wateredPlantService.create(newWateredPlant);
    WateredPlantResponse wateredPlantResponse = new WateredPlantResponse();
    BeanUtils.copyProperties(wateredPlantCreated, wateredPlantResponse);
    wateredPlantResponse.setDaysSinceLastDayWatering(
        DateUtil.getDaysSinceLastWatering(wateredPlantCreated.getLastDayWatering()));

    return wateredPlantResponse;
  }

  @ApiOperation(value = "Actualizar una  planta regada por id",
      notes = "Servicio para actualizar una planta regada")
  @ApiResponses(
      value = { @ApiResponse(code = 201, message = "Planta regada actualizada correctamente"),
          @ApiResponse(code = 400, message = "Solicitud inválida"),
          @ApiResponse(code = 500, message = "Error en el servidor") })
  @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public WateredPlantResponse updateWateredPlant(
      @RequestBody UpdateWateredPlantRequest updateWateredPlantRequest) {

    WateredPlant updateWateredPlant = new WateredPlant();
    BeanUtils.copyProperties(updateWateredPlantRequest, updateWateredPlant);

    WateredPlant wateredPlantUpdated = wateredPlantService.update(updateWateredPlant);

    WateredPlantResponse wateredPlantResponse = new WateredPlantResponse();
    BeanUtils.copyProperties(wateredPlantUpdated, wateredPlantResponse);
    wateredPlantResponse.setDaysSinceLastDayWatering(
        DateUtil.getDaysSinceLastWatering(wateredPlantUpdated.getLastDayWatering()));

    return wateredPlantResponse;
  }

  @ApiOperation(value = "Agregar una imagen de planta ",
      notes = "Servicio para agregar una imagen de una planta ")
  @ApiResponses(value = { @ApiResponse(code = 201, message = "Imagen agregada correctamente"),
      @ApiResponse(code = 400, message = "Solicitud inválida"),
      @ApiResponse(code = 500, message = "Error en el servidor") })
  @PostMapping(value = "/image", headers = ("Content-Type=multipart/form-data"))
  public WateredPlantResponse uploadPlantImage(@RequestParam() Long id,
      @RequestParam("image") MultipartFile multipartFile) {
    WateredPlant wateredPlantUpdated = wateredPlantService.updateImagePlant(id, multipartFile);
    WateredPlantResponse wateredPlantResponse = new WateredPlantResponse();

    BeanUtils.copyProperties(wateredPlantUpdated, wateredPlantResponse);
    wateredPlantResponse.setDaysSinceLastDayWatering(
        DateUtil.getDaysSinceLastWatering(wateredPlantUpdated.getLastDayWatering()));
    return wateredPlantResponse;
  }

  @ApiOperation(value = "Actualizar último día de riego",
      notes = "Servicio para Actualizar último día de riego")
  @ApiResponses(value = { @ApiResponse(code = 201, message = "Planta actualizada correctamente"),
      @ApiResponse(code = 400, message = "Solicitud inválida"),
      @ApiResponse(code = 500, message = "Error en el servidor") })
  @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public WateredPlantResponse waterPlant(@PathVariable Long id,
      @RequestBody UpdateLastDayWateringRequest newLastDayWatered) {

    WateredPlant wateredPlantPatched =
        wateredPlantService.updateLastDayWatering(id, newLastDayWatered.getLastDayWatering());

    WateredPlantResponse wateredPlantResponse = new WateredPlantResponse();

    BeanUtils.copyProperties(wateredPlantPatched, wateredPlantResponse);
    wateredPlantResponse.setDaysSinceLastDayWatering(
        DateUtil.getDaysSinceLastWatering(wateredPlantPatched.getLastDayWatering()));

    return wateredPlantResponse;
  }

}
