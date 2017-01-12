package rs.ac.bg.fon.silab.dms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rs.ac.bg.fon.silab.dms.core.model.Role;
import rs.ac.bg.fon.silab.dms.security.CustomAuthenticationProvider;
import rs.ac.bg.fon.silab.dms.security.SecurityFilter;

@Configuration
public class SecurityConfiguration {

    @EnableWebSecurity
    @Configuration
    protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(new SecurityFilter(), UsernamePasswordAuthenticationFilter.class);

            http.authorizeRequests()
                    .antMatchers("/dms/auth/**").permitAll()
                    .antMatchers("/dms/api-status").permitAll()
                    .antMatchers("/dms/admin").hasAuthority(Role.ADMIN.toString())
                    .antMatchers("/dms/documents").permitAll()
                    .antMatchers("/dms/processes").permitAll()
                    .antMatchers("/dms/auth/logout").authenticated()
                    .antMatchers("/dms/**").authenticated();

            http.exceptionHandling()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .csrf().disable();
        }
    }
}
