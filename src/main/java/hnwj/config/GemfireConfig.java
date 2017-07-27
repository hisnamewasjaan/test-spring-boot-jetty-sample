package hnwj.config;

import com.gemstone.gemfire.cache.GemFireCache;
import hnwj.jetty.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import java.util.Properties;


/**
 */
@EnableAutoConfiguration
@Configuration
@EnableGemfireRepositories(basePackages = "hnwj.data.gemfire")
@Profile("gemfire")
public class GemfireConfig {

    private static final Logger LOG = LoggerFactory.getLogger(GemfireConfig.class);


    @Bean
    Properties gemfireProperties() {
        Properties properties = new Properties();
        properties.setProperty("name", "DataGemFireRestApplication");
        properties.setProperty("mcast-port", "0");
        properties.setProperty("log-level", "config");
        LOG.info("*** gemfire props: '{}'", properties);

        return properties;
    }

    @Bean
    CacheFactoryBean gemfireCache() {
        CacheFactoryBean gemfireCache = new CacheFactoryBean();
        gemfireCache.setClose(true);
        gemfireCache.setProperties(gemfireProperties());
        LOG.info("*** gemfire cache: '{}'", gemfireCache);
        return gemfireCache;
    }

    @Bean
    LocalRegionFactoryBean<String, Person> helloRegion(GemFireCache cache) {
        LocalRegionFactoryBean<String, Person> regionFactoryBean = new LocalRegionFactoryBean<>();
        regionFactoryBean.setCache(cache);
        regionFactoryBean.setClose(true);
        regionFactoryBean.setName("hello");
        regionFactoryBean.setPersistent(false);
        LOG.info("*** LocalRegionFactoryBean: '{}'", regionFactoryBean);
        return regionFactoryBean;
    }

}
