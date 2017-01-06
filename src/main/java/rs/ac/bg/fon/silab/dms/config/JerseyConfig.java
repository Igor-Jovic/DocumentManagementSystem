package rs.ac.bg.fon.silab.dms.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dms.controller.HomeController;
import rs.ac.bg.fon.silab.dms.controller.registration.RegistrationController;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(HomeController.class);
    }
}
