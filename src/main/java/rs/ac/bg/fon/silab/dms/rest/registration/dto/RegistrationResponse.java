package rs.ac.bg.fon.silab.dms.rest.registration.dto;


public class RegistrationResponse {
    public String username;
    public String companyName;
    public String userRole;


    public RegistrationResponse() {
    }

    public RegistrationResponse(String username, String userRole, String companyName) {
        this.userRole = userRole;
        this.username = username;
        this.companyName = companyName;
    }
}
