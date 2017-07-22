package hnwj.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configures basic security
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOG.info("*** configure(HttpSecurity http) ***");
        http.authorizeRequests()
                .antMatchers("/", "/index", "/sample")
//                .antMatchers("/", "/index", "/sample", "/ws/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()  //enables form login
                .and()
                .authorizeRequests()
                .antMatchers("/sample/hello")
                .permitAll()
                .anyRequest()
                .hasRole("USER")
        ;

        http.antMatcher("/ws/**")
                .csrf().disable()
                .httpBasic()
        ;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        LOG.info("*** configure(WebSecurity web) ***");
        /* allows javascript to run */
        web.ignoring().antMatchers("/webjars/**");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        LOG.info("*** configure(AuthenticationManagerBuilder auth) ***");
        auth.inMemoryAuthentication()
                .withUser("jan").password("pjan").roles("USER")
                .and()
                .withUser("admin").password("padmin").roles("ADMIN", "USER")
                .and()
                .withUser("anon").password("panon").roles("ANON")
        ;
    }
}
