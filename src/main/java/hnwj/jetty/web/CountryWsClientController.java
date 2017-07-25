package hnwj.jetty.web;

import hnwj.ws.client.CountryClient;
import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 */
@Controller
public class CountryWsClientController {

    private static final Logger LOG = LoggerFactory.getLogger(CountryWsClientController.class);

    @Autowired
    CountryClient getCountryClient;

    @RequestMapping("/country/spain")
    public String spain(Model model) {
        LOG.info("Sending country request for spain");

        GetCountryRequest getCountryRequest = new GetCountryRequest();
        getCountryRequest.setName("Spain");
        GetCountryResponse countryResponse = getCountryClient.getCountry(getCountryRequest);


        LOG.info("Adding country response '{}' to model attributes", countryResponse);
        model.addAttribute("country", countryResponse);
        model.addAttribute("country", countryResponse.getCountry());
        model.addAttribute("country_name", countryResponse.getCountry().getName());
        model.addAttribute("country_capital", countryResponse.getCountry().getCapital());
        model.addAttribute("country_currency", countryResponse.getCountry().getCurrency());
        model.addAttribute("country_population", countryResponse.getCountry().getPopulation());


        return "country";
    }
}
