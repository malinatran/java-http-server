package com.malinatran.utility;

import com.malinatran.request.Request;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorizerTest {

    private Request request;
    private boolean result;

    @Before
    public void setUp() {
        request = new Request();
    }

    @Test
    public void hasValidRouteAndCredentialsReturnsTrueWithCorrectRouteAndCredentials() {
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic YWRtaW46aHVudGVyMg==");

        result = Authorizer.hasValidRouteAndCredentials(request);

        assertTrue(result);
    }

    @Test
    public void hasValidCredentialsReturnsFalseWithIncorrectCredentials() {
        result = Authorizer.hasValidCredentials("Basic Hello");

        assertFalse(result);
    }
}