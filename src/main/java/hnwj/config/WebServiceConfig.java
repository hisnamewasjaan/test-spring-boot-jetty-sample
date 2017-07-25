package hnwj.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;
import java.util.Properties;

/**
 * Spring WS SOAP handler configuration
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {


    @Bean
    public org.springframework.ws.soap.security.wss4j.callback.SimplePasswordValidationCallbackHandler passwordValidationCallbackHandler() {
        SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler = new SimplePasswordValidationCallbackHandler();
        Properties users = new Properties();
        users.setProperty("jan", "pjan");
        simplePasswordValidationCallbackHandler.setUsers(users);
        return simplePasswordValidationCallbackHandler;
    }

    @Bean
    public org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler passwordValidationCallbackHandler2() {
        org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler = new org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler();
        Properties users = new Properties();
        users.setProperty("jan", "pjan");
        simplePasswordValidationCallbackHandler.setUsers(users);
        return simplePasswordValidationCallbackHandler;
    }

    @Bean
    public org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor wss4jSecurityInterceptor() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
//        securityInterceptor.setSecurementActions("UsernameToken");
        securityInterceptor.setValidationActions("UsernameToken");
        securityInterceptor.setValidationCallbackHandler(passwordValidationCallbackHandler());
        return securityInterceptor;
    }

    @Bean
    public org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor wss4jSecurityInterceptor2() {
        org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor securityInterceptor = new org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor();
//        securityInterceptor.setSecurementActions("UsernameToken");
        securityInterceptor.setValidationActions("UsernameToken");
        securityInterceptor.setValidationCallbackHandler(passwordValidationCallbackHandler2());
        return securityInterceptor;
    }


    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        super.addInterceptors(interceptors);
        interceptors.add(wss4jSecurityInterceptor());
//        interceptors.add(wss4jSecurityInterceptor2());
    }

    /**
     * Constructs the dispatcher servlet for SOAP (web service) messages.
     * <p>
     * By naming this bean 'messageDispatcherServlet',
     * it does NOT replace Spring Boot’s default DispatcherServlet bean
     *
     * @param applicationContext
     * @return ServletRegistrationBean - The servlet mapping URI pattern on
     * the ServletRegistrationBean is set to “/ws/*”.
     * The web container will use this path to map incoming HTTP requests to the servlet.
     */
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        /*
        the MessageDispatcherServlet combines the attributes of the
        MessageDispatcher and DispatcherServlet and as a result allows
        the handling of XML messages over HTTP
        */
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    /**
     * Exposes a standard WSDL 1.1 using XsdSchema
     *
     * @param countriesSchema
     * @return
     */
    @Bean(name = "countries")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
        defaultWsdl11Definition.setPortTypeName("CountriesPort");
        defaultWsdl11Definition.setLocationUri("/ws");
        defaultWsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        defaultWsdl11Definition.setSchema(countriesSchema);
        return defaultWsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }

}
