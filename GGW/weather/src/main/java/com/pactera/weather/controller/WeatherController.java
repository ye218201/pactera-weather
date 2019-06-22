package com.pactera.weather.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pactera.weather.entity.WeatherEntity;
import com.pactera.weather.service.WeatherService;

@Controller
public class WeatherController {
	
	@Resource
	private WeatherService weatherSercice;
	@RequestMapping("/getAllWeatherInfor")
	@ResponseBody
	public List<WeatherEntity> getAllWeatherInformation() {
		return weatherSercice.getAllWeatherInformation();
	}
}
