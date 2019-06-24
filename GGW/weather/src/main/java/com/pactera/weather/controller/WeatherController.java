package com.pactera.weather.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pactera.weather.entity.WeatherEntity;
import com.pactera.weather.service.WeatherService;

@Controller
public class WeatherController {
	
	@Resource
	private WeatherService weatherSercice;
	
	@GetMapping("search/{cityName}")
	@ResponseBody
	public List<WeatherEntity> getAllWeatherInformation(@PathVariable("cityName") String cityName) {
		System.out.println(cityName);
		return weatherSercice.getAllWeatherInformation(cityName);
	}
	
	@RequestMapping("/getCityName")
	public ModelAndView getCityName(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mode = new ModelAndView("search");
		mode.addObject("cityName",weatherSercice.getCityName());
		StringBuffer path = request.getRequestURL();
		path = path.delete(path.length()-11, path.length());
		mode.addObject("path",path);
		return mode;
	}
}
