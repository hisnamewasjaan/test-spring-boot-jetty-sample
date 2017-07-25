package hnwj.ws.client;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 */
public class CountryClient extends WebServiceGatewaySupport {

    private static final Logger LOG = LoggerFactory.getLogger(CountryClient.class);

    public GetCountryResponse getCountry(GetCountryRequest getCountryRequest) {
        LOG.info("Sending request '{}'", getCountryRequest);
        return (GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive(getCountryRequest);
    }
}
