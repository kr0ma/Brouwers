package be.vdab.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.vdab.restclients.WeatherMapClient;

@Service
public class TemperatuurServiceImpl implements TemperatuurService {
	private final WeatherMapClient weatherMapClient;
	
	@Autowired
	public TemperatuurServiceImpl(WeatherMapClient weatherMapClient) {
		this.weatherMapClient = weatherMapClient;
	}
	
	@Override
	public BigDecimal getTemperature(String plaats) {
		return weatherMapClient.getTemperature(plaats);
	}

}
