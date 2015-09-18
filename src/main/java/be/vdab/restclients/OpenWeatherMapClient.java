package be.vdab.restclients;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import be.vdab.exceptions.KanTemperatuurNietLezenException;

@Component
class OpenWeatherMapClient implements WeatherMapClient{
	private final static Logger logger = Logger.getLogger(OpenWeatherMapClient.class.getName());

	private final String uriTemplate;
	private final RestTemplate restTemplate;
	
	@Autowired
	OpenWeatherMapClient(@Value("${openWeatherMapURL}") String uriTemplate, RestTemplate restTemplate) {
		this.uriTemplate = uriTemplate;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public BigDecimal getTemperature(String plaats) {
		try {
			Current current = restTemplate.getForObject(uriTemplate, Current.class, plaats);
			return current.temperature.value;
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "kan koers niet lezen", ex);
			throw new KanTemperatuurNietLezenException();
		}
	}

}
