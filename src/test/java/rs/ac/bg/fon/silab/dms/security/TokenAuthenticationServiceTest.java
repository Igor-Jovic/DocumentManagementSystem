package rs.ac.bg.fon.silab.dms.security;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TokenAuthenticationServiceTest {


    private TokenAuthenticationService testee = new TokenAuthenticationService();

    @Test
    public void getAuthenticationData_cachedDataEmpty_returnsNull() {
        AuthenticationData authenticationData = testee.getAuthenticationData("token");
        assertNull(authenticationData);
    }

    @Test
    public void getAuthenticationData_tenMinutesAfterTokenInvalidation_returnsNullAndTokenRemoved() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("", "", new ArrayList<GrantedAuthority>());
        LocalDateTime authenticationTime = LocalDateTime.now().minusMinutes(testee.tokenLifeInMinutes + 10);
        AuthenticationData authenticationData = testee.saveAuthentication(authentication, authenticationTime);

        int numberOfUsersBefore = testee.getCurrentNumberOfUsers();
        authenticationData = testee.getAuthenticationData(authenticationData.getToken());

        assertNull(authenticationData);
        assertEquals(numberOfUsersBefore - 1, testee.getCurrentNumberOfUsers());
    }

    @Test
    public void getAuthenticationData_tenMinutesUntilTokenInvalidation_returnsAuthData() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("admin", "admin", new ArrayList<GrantedAuthority>());
        LocalDateTime authenticationTime = LocalDateTime.now().minusMinutes(testee.tokenLifeInMinutes - 10);
        AuthenticationData authenticationData = testee.saveAuthentication(authentication, authenticationTime);

        authenticationData = testee.getAuthenticationData(authenticationData.getToken());

        assertEquals(authentication, authenticationData.getAuthentication());
        assertEquals(1, testee.getCurrentNumberOfUsers());
    }
}