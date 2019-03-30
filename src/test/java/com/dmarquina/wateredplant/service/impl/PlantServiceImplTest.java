package com.dmarquina.wateredplant.service.impl;

import com.dmarquina.wateredplant.model.Plant;
import com.dmarquina.wateredplant.repository.PlantRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlantServiceImplTest {

  @InjectMocks
  PlantServiceImpl plantService;

  @Mock
  PlantRepository plantRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void create_plant_ok() {
    when(plantRepository.save(any())).thenReturn(new Plant());
    Plant plant = mock(Plant.class);
    Plant newPlant = plantService.create(plant);
    assertThat(new Plant(), is(newPlant));
  }

}