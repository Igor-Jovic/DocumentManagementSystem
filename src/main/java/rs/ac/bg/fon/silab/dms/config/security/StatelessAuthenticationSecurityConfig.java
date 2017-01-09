package rs.ac.bg.fon.silab.dms.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("WEB SECURITY CONFIG");
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();
    }
}
