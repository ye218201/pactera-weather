package com.pactera.weather;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pactera.weather.service.WeatherService;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestWeatherService {
	@Autowired
	private WeatherService service;
	
	@Test
    public void testGetCityNamed() throws Exception{
		Assert.assertNotNull(service.getCityName());
	}
	
	@Test
    public void testGetAllWeatherInformation() throws Exception{
		Assert.assertNotNull(service.getAllWeatherInformation("beijin"));
	}
}
