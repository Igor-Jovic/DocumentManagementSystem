package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration.dto;


public class RegistrationResponse {
    public String message;


    public RegistrationResponse() {
    }

    public RegistrationResponse(String username, String userRole, String companyName) {
        this.message = String.format("Registration successful for %s: %s@%s, you can now login.",
                userRole.toLowerCase(), username, companyName);
    }
}
