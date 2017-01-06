package rs.ac.bg.fon.silab.dms.rest.registration.dto;


public class RegistrationRequest {
    public String username;
    public String password;
    public String companyName;

    public boolean isValid() {
        return !isStringEmptyOrNull(username)
                && !isStringEmptyOrNull(password)
                && !isStringEmptyOrNull(companyName);
    }

    private boolean isStringEmptyOrNull(String string) {
        return string == null || string.trim().isEmpty();
    }
}
