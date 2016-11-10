package com.malinatran.utility;

import com.malinatran.request.Request;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorizerTest {

    @Test
    public void hasValidCredentialsReturnsFalseWithIncorrectCredentials() {
        boolean result = Authorizer.hasValidCredentials("Basic Hello");

        assertFalse(result);
    }

    @Test
    public void hasValidRouteAndCredentialsReturnsTrueWithCorrectRouteAndCredentials() {
        Request request = new Request();
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader(Header.AUTHORIZATION + ": " + System.getenv("COB_SPEC_CREDENTIALS"));

        boolean result = Authorizer.hasValidRouteAndCredentials(request);

        assertTrue(result);
    }
}