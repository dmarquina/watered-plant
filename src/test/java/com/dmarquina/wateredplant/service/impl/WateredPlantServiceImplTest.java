package com.dmarquina.wateredplant.service.impl;

import com.dmarquina.wateredplant.model.WateredPlant;
import com.dmarquina.wateredplant.repository.WateredPlantRepository;

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

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
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