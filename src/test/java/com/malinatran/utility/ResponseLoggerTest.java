package com.malinatran.utility;

import com.malinatran.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseLoggerTest {

    private ResponseLogger logger;
    private Response response;

    @Before
    public void setUp() {
        logger = new ResponseLogger();
        response = new Response("HTTP/1.1");
    }

    @Test
    public void logResponseSetsResponse() throws Exception {
        logger.logResponse(response);

        assertEquals(response, logger.getLoggedResponse());
    }

    @Test
    public void getLoggedResponseReturnsResponse() throws Exception {
        logger.logResponse(response);

        Response loggedResponse = logger.getLoggedResponse();

        assertEquals(response, loggedResponse);
    }
}