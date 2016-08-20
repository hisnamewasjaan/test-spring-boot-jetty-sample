package hnwj.jetty.web;

import hnwj.jetty.service.WeatherClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import weather.wsdl.GetCityForecastByZIPResponse;

/**
 */
@RestController
public class WeatherController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WeatherClient weatherClient;


    @GetMapping("/weather")
    @ResponseBody
    public String weatherByZip(@RequestParam(value = "zipCode", defaultValue = "35801") String zipCode) {
        log.debug("weather - debug");
        log.info("weather - info");
        log.warn("weather - warn");
        log.error("weather - error");
        GetCityForecastByZIPResponse cityForecastByZip = weatherClient.getCityForecastByZip(zipCode);
        return String.format("<html><head><title>Weather</title></head><body>%s</body></html>",
                weatherClient.responseToString(cityForecastByZip));
    }

    @GetMapping("/rest/weather")
    @ResponseBody
    public GetCityForecastByZIPResponse restWeatherByZip(@RequestParam(value = "zipCode", defaultValue = "35801") String zipCode) {
        log.debug("weather - debug");
        log.info("weather - info");
        log.warn("weather - warn");
        log.error("weather - error");
        GetCityForecastByZIPResponse cityForecastByZip = weatherClient.getCityForecastByZip(zipCode);
        return cityForecastByZip;
    }
}
