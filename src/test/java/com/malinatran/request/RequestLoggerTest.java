package com.malinatran.request;

import org.junit.Test;
import static org.junit.Assert.*;

public class RequestLoggerTest {

    @Test
    public void addRequestLineAddsStringToLogger() {
        RequestLogger requestLogger = new RequestLogger();
        Request request = new Request();
        request.setRequestLine("GET / HTTP/1.1");

        requestLogger.addRequestLine(request);

        assertEquals("GET / HTTP/1.1\r\n", requestLogger.getLoggedRequests());
    }

    @Test
    public void addRequestLineAddsMultipleStringsToLogger() {
        RequestLogger requestLogger = new RequestLogger();
        Request firstRequest = new Request();
        firstRequest.setRequestLine("PUT /these HTTP/1.1");
        Request secondRequest = new Request();
        secondRequest.setRequestLine("GET /logs HTTP/1.1");

        requestLogger.addRequestLine(firstRequest);
        requestLogger.addRequestLine(secondRequest);

        assertEquals("PUT /these HTTP/1.1\r\nGET /logs HTTP/1.1\r\n", requestLogger.getLoggedRequests());
    }
}