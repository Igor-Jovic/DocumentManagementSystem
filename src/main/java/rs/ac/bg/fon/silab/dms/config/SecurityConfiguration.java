package rs.ac.bg.fon.silab.dms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.security.SecurityFilter;

import static rs.ac.bg.fon.silab.dms.core.model.Role.ADMIN;

@Configuration
public class SecurityConfiguration {

    @EnableWebSecurity
    @Configuration
    protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(new SecurityFilter(), UsernamePasswordAuthenticationFilter.class);

            http.authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/documents").authenticated()
                    .antMatchers("/auth/logout").authenticated()
                    .antMatchers("/auth/user").authenticated()
                    .antMatchers("/documenttypes").authenticated()
                    .antMatchers("/users").hasAuthority(ADMIN.toString())
                    .antMatchers("/processes").hasAuthority(ADMIN.toString())
                    .antMatchers("/activities").hasAuthority(ADMIN.toString())
                    .antMatchers("/**").permitAll();

            http.exceptionHandling()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .csrf().disable();
        }
    }
}
