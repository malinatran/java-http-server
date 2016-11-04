package com.malinatran.request;

import org.junit.Before;
import org.junit.Test;

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
    public void addRequestLineAddsStringToLogger() {
        request.setRequestLine("GET / HTTP/1.1");

        logger.addRequestLine(request);

        assertEquals("GET / HTTP/1.1\r\n", logger.getLoggedRequests());
    }

    @Test
    public void addRequestLineAddsMultipleStringsToLogger() {
        request.setRequestLine("PUT /these HTTP/1.1");
        secondRequest.setRequestLine("GET /logs HTTP/1.1");

        logger.addRequestLine(request);
        logger.addRequestLine(secondRequest);

        assertEquals("PUT /these HTTP/1.1\r\nGET /logs HTTP/1.1\r\n", logger.getLoggedRequests());
    }

}