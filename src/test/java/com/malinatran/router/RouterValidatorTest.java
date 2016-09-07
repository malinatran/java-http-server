package com.malinatran.router;

import com.malinatran.request.Request;
import org.junit.Test;
import static org.junit.Assert.*;

public class RouterValidatorTest {

    @Test
    public void isValidRouteAndCredentialsReturnsTrueWithCorrectRouteAndCredentials() {
        Request request = new Request();
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic YWRtaW46aHVudGVyMg==");
        RouterValidator validator = new RouterValidator();

        Boolean isValidCredentials = validator.isValidRouteAndCredentials(request);

        assertTrue(isValidCredentials);
    }

    @Test
    public void isValidCredentialsReturnsFalseWithIncorrectCredentials() {
        RouterValidator validator = new RouterValidator();

        Boolean isValidCredentials = validator.isValidCredentials("Basic Hello");

        assertFalse(isValidCredentials);
    }
}