package com.malinatran.utility;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorizerTest {

    @Test
    public void hasValidRouteAndCredentialsReturnsTrueWithCorrectRouteAndCredentials() {
        String method = "GET";
        String path = "/logs";
        String credentials = System.getenv("JAVA_SERVER_TOKEN");

        boolean result = Authorizer.hasValidRouteAndCredentials(method, path, credentials);

        assertTrue(result);
    }

    @Test
    public void hasValidRouteAndCredentialsReturnsFalseWithIncorrectMethod() {
        String method = "POST";
        String path = "/logs";
        String credentials =  System.getenv("JAVA_SERVER_TOKEN");

        boolean result = Authorizer.hasValidRouteAndCredentials(method, path, credentials);

        assertFalse(result);
    }

    @Test
    public void hasValidRouteAndCredentialsReturnsFalseWithIncorrectPath() {
        String method = "GET";
        String path = "/log";
        String credentials =  System.getenv("JAVA_SERVER_TOKEN");

        boolean result = Authorizer.hasValidRouteAndCredentials(method, path, credentials);

        assertFalse(result);
    }

    @Test
    public void hasValidRouteAndCredentialsReturnsFalseWithIncorrectCredentials() {
        String method = "GET";
        String path = "/logs";
        String credentials = "Hello world";

        boolean result = Authorizer.hasValidRouteAndCredentials(method, path, credentials);

        assertFalse(result);
    }
}