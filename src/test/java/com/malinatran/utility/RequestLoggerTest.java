package com.malinatran.utility;

import com.malinatran.request.Request;
import com.malinatran.response.Formatter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void logRequestStoresStringToLogger() {
        request.setRequestLine("GET / HTTP/1.1");

        logger.logRequest(request);

        assertEquals("GET / HTTP/1.1" + Formatter.CRLF, logger.getLoggedRequests());
    }

    @Test
    public void logRequestStoresMultipleStringsToLogger() {
        request.setRequestLine("PUT /these HTTP/1.1");
        secondRequest.setRequestLine("GET /logs HTTP/1.1");

        logger.logRequest(request);
        logger.logRequest(secondRequest);

        assertEquals("PUT /these HTTP/1.1" + Formatter.CRLF + "GET /logs HTTP/1.1" + Formatter.CRLF, logger.getLoggedRequests());
    }
}