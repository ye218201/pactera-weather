package com.pactera.weather.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pactera.weather.entity.WeatherEntity;
import com.pactera.weather.util.JsonUtils;

@Service
public class WeatherService {
	
	private static Map<String,String> weatherConditions = new HashMap<>();
	static {
		weatherConditions.put("xue", "Snow");
		weatherConditions.put("lei", "Thunder");
		weatherConditions.put("shachen", "Dust and sand weather");
		weatherConditions.put("wu", "Greasy weather ");
		weatherConditions.put("bingbao", "Hail weather");
		weatherConditions.put("yun", "Cloud");
		weatherConditions.put("yu", "Rain");
		weatherConditions.put("yin", "Overcast");
		weatherConditions.put("qing", "Clear weather");
	}
	
	@Value("${cityInfomation}")
	private String cityInfomation;
	@Value("${Url}")
	private String weatherUrl;

	@SuppressWarnings("rawtypes")
	public List<WeatherEntity> getAllWeatherInformation() {
		Map<String, String> cityInformationMap = getCityInformaton();
		List<WeatherEntity> AllWeatherInformation = new ArrayList<>();
		for (String key : cityInformationMap.keySet()) {
			WeatherEntity weatherEntity = new WeatherEntity();
			String url = weatherUrl+cityInformationMap.get(key);
			Map <String, String> weatherMap = JsonUtils.readJson2Bean(httpRequest(url), Map.class);
			weatherEntity.setTemperature(weatherMap.get("tem2")+"°C~"+weatherMap.get("tem1")+"°C");
			weatherEntity.setUpdated_time(weatherMap.get("update_time"));
			weatherEntity.setWeather(weatherConditions.get(weatherMap.get("wea_img")));
			weatherEntity.setWind(weatherMap.get("win_speed").substring(0, weatherMap.get("win_speed").length()-2)+"grade");
			weatherEntity.setCity(weatherMap.get("cityEn"));
			AllWeatherInformation.add(weatherEntity);
		}
		return AllWeatherInformation;
		
	}

	private Map getCityInformaton() {
		Map<String, String> cityInformationMap = new HashMap<>();
		String cityNameAndCityId[] = cityInfomation.split(",");
		for (int i = 0; i < cityNameAndCityId.length; i++) {
			String cityValAndKey[] = cityNameAndCityId[i].split(":");
			cityInformationMap.put(cityValAndKey[0], cityValAndKey[1]);
		}
		return cityInformationMap;
	}

	private String httpRequest(String req_url) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(req_url);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
