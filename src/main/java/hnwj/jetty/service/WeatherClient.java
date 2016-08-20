package hnwj.jetty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import weather.wsdl.*;

import java.text.SimpleDateFormat;

/**
 */
public class WeatherClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(WeatherClient.class);

    public GetCityForecastByZIPResponse getCityForecastByZip(String zipCode) {
        GetCityForecastByZIP request = new GetCityForecastByZIP();
        request.setZIP(zipCode);

        log.info(String.format("Requesting forecast for %s", zipCode));

        GetCityForecastByZIPResponse response = (GetCityForecastByZIPResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://wsf.cdyne.com/WeatherWS/Weather.asmx",
                        request,
                        new SoapActionCallback("http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP"));

        return response;
    }

    public void printResponse(GetCityForecastByZIPResponse response) {
        ForecastReturn forecastReturn = response.getGetCityForecastByZIPResult();
        if (forecastReturn.isSuccess()) {

            log.info(String.format("Forecast for %s, %s", forecastReturn.getCity(), forecastReturn.getState()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Forecast forecast : forecastReturn.getForecastResult().getForecast()) {
                Temp temperatures = forecast.getTemperatures();

                log.info(String.format("%s %s %s %s°-%s°",
                        simpleDateFormat.format(forecast.getDate().toGregorianCalendar().getTime()),
                        forecast.getDesciption(),
                        temperatures.getMorningLow(),
                        temperatures.getDaytimeHigh()));
                log.info("");

            }
        } else {
            log.info("No forecast received");
        }
    }

    public String responseToString(GetCityForecastByZIPResponse response) {
        StringBuilder stringBuilder = new StringBuilder();

        ForecastReturn forecastReturn = response.getGetCityForecastByZIPResult();
        if (forecastReturn.isSuccess()) {

            String forecastFor = String.format("Forecast for %s, %s", forecastReturn.getCity(), forecastReturn.getState());
            log.info(forecastFor);
            stringBuilder.append(forecastFor);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Forecast forecast : forecastReturn.getForecastResult().getForecast()) {
                Temp temperatures = forecast.getTemperatures();

                String formattedForecast = String.format("%s %s %s°-%s°",
                        simpleDateFormat.format(forecast.getDate().toGregorianCalendar().getTime()),
                        forecast.getDesciption(),
                        temperatures.getMorningLow(),
                        temperatures.getDaytimeHigh());
                log.info(formattedForecast);
                stringBuilder.append("<br>");
                stringBuilder.append(formattedForecast);
                log.info("");

            }
        } else {
            String noForecast = "No forecast received";
            log.info(noForecast);
            stringBuilder.append(noForecast);
        }
        return stringBuilder.toString();
    }
}
