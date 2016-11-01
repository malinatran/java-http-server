package com.malinatran.router;

import com.malinatran.request.Request;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouterValidatorTest {

    private Request request;
    private RouterValidator validator;
    private Boolean result;

    @Before
    public void setUp() {
        request = new Request();
        validator = new RouterValidator();
    }

    @Test
    public void isValidRouteAndCredentialsReturnsTrueWithCorrectRouteAndCredentials() {
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic YWRtaW46aHVudGVyMg==");

        result = validator.isValidRouteAndCredentials(request);

        assertTrue(result);
    }

    @Test
    public void isValidCredentialsReturnsFalseWithIncorrectCredentials() {
        result = validator.isValidCredentials("Basic Hello");

        assertFalse(result);
    }
}