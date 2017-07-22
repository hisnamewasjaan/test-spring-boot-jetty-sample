package hnwj.jetty.domain;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 */
@Component
public class CountryRepository {

    private static final Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        Country spain = newCountry("Spain", "Madrid", Currency.EUR, 46704314);
        Country poland = newCountry("Poland", "Warsaw", Currency.PLN, 38186860);
        Country uk = newCountry("United Kingdom", "London", Currency.GBP, 63705000);


        countries.put(spain.getName(), spain);
        countries.put(poland.getName(), poland);
        countries.put(uk.getName(), uk);
    }

    public Country findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }

    private static Country newCountry(String name, String capital, Currency currency, int population) {
        Country country = new Country();
        country.setName(name);
        country.setCapital(capital);
        country.setCurrency(currency);
        country.setPopulation(population);
        return country;
    }
}
