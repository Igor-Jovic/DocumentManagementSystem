package rs.ac.bg.fon.silab.dms.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dms.rest.HomeController;
import rs.ac.bg.fon.silab.dms.rest.authentication.registration.RegistrationRestService;
import rs.ac.bg.fon.silab.dms.rest.documentType.DocumentTypeRestService;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("rs.ac.bg.fon.silab.dms");
        register(HomeController.class);
        register(RegistrationRestService.class);
        register(DocumentTypeRestService.class);
    }
}
