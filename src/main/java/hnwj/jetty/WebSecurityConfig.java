package hnwj.jetty;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/sample")
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
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        /* allows javascript to run */
        web.ignoring().antMatchers("/webjars/**");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("jan").password("pjan").roles("USER")
                .and()
                .withUser("admin").password("padmin").roles("ADMIN", "USER")
                .and()
                .withUser("anon").password("panon").roles("ANON")
        ;
    }
}
