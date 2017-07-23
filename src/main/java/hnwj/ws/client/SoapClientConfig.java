package hnwj.ws.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;

/**
 */
@Configuration
public class SoapClientConfig {


    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
        wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
//        wss4jSecurityInterceptor.setSecurementActions("Timestamp UsernameToken");
        wss4jSecurityInterceptor.setSecurementUsername("jan");
        wss4jSecurityInterceptor.setSecurementPassword("pjan");
        return wss4jSecurityInterceptor;
    }

    @Bean
    public Jaxb2Marshaller getMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("io.spring.guides.gs_producing_web_service");
        return jaxb2Marshaller;
    }

    @Bean
    public CountryClient getCountryClient() {
        CountryClient countryClient = new CountryClient();
        countryClient.setMarshaller(getMarshaller());
        countryClient.setUnmarshaller(getMarshaller());
        countryClient.setDefaultUri("http://localhost:8080/ws/");
        countryClient.setInterceptors(new ClientInterceptor[]{securityInterceptor()});
        return countryClient;
    }
}
