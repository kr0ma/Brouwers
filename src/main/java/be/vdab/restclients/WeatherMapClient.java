package be.vdab.restclients;

import java.math.BigDecimal;

public interface WeatherMapClient {
	BigDecimal getTemperature(String plaats);
}
