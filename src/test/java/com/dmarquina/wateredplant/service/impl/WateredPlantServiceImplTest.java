package com.dmarquina.wateredplant.service.impl;

import com.dmarquina.wateredplant.model.Plant;
import com.dmarquina.wateredplant.model.WateredPlant;
import com.dmarquina.wateredplant.repository.WateredPlantRepository;
import com.dmarquina.wateredplant.service.PlantService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WateredPlantServiceImplTest {
  @InjectMocks
  WateredPlantServiceImpl wateredPlantService;

  @Mock
  WateredPlantRepository wateredPlantRepository;

  @Mock
  private PlantService plantService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void create_watered_plant_ok() {
    Plant dummyPlant = mock(Plant.class);
    when(wateredPlantRepository.save(any())).thenReturn(new WateredPlant());
    when(plantService.create(any())).thenReturn(dummyPlant);
    when(dummyPlant.getId()).thenReturn(new Long(1));

    WateredPlant wateredPlant = mock(WateredPlant.class);
    when(wateredPlant.getPlant()).thenReturn(dummyPlant);
    WateredPlant newWateredPlant = wateredPlantService.create(wateredPlant);
    assertThat(new WateredPlant(), is(newWateredPlant));
  }

  @Test
  public void list_watered_plants_ok() {
    List mockList = Mockito.mock(ArrayList.class);
    mockList.add(new WateredPlant());
    mockList.add(new WateredPlant());
    when(wateredPlantRepository.findAll()).thenReturn(mockList);
    List<WateredPlant> list = wateredPlantService.findAll();
    assertThat(mockList, is(list));
  }
}