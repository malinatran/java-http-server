package com.malinatran.utility;

import com.malinatran.request.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class RequestLoggerTest {

    private RequestLogger logger;
    private Request request;
    private Request secondRequest;

    @Before
    public void setUp() {
        logger = new RequestLogger();
        request = new Request();
        secondRequest = new Request();
    }

    @Test
    public void logRequestStoresStringToLogger() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        request.setRequestLine("GET / HTTP/1.1");

        logger.logRequest(request);

        assertEquals("GET / HTTP/1.1\r\n", logger.getLoggedRequests());
    }

    @Test
    public void logRequestStoresMultipleStringsToLogger() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        request.setRequestLine("PUT /these HTTP/1.1");
        secondRequest.setRequestLine("GET /logs HTTP/1.1");

        logger.logRequest(request);
        logger.logRequest(secondRequest);

        assertEquals("PUT /these HTTP/1.1\r\nGET /logs HTTP/1.1\r\n", logger.getLoggedRequests());
    }
}