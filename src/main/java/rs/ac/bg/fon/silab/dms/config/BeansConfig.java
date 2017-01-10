package rs.ac.bg.fon.silab.dms.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

@Configuration
public class BeansConfig {

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return TokenAuthenticationService.getInstance();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
