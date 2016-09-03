package com.malinatran.router;

import com.malinatran.request.Request;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoggerTest {

    @Test
    public void testAddRequestLine() {
        Logger logger = new Logger();
        Request request = new Request();
        request.setRequestLine("GET / HTTP/1.1");

        logger.addRequestLine(request);

        assertEquals("GET / HTTP/1.1\r\n", logger.getLoggedRequests());
    }

    @Test
    public void testMultipleRequestLines() {
        Logger logger = new Logger();
        Request firstRequest = new Request();
        firstRequest.setRequestLine("PUT /these HTTP/1.1");
        Request secondRequest = new Request();
        secondRequest.setRequestLine("GET /logs HTTP/1.1");

        logger.addRequestLine(firstRequest);
        logger.addRequestLine(secondRequest);

        assertEquals("PUT /these HTTP/1.1\r\nGET /logs HTTP/1.1\r\n", logger.getLoggedRequests());
    }
}