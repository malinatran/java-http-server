package com.malinatran.routing;

import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;


import static org.junit.Assert.*;

public class RedirectActionTest {

    private Action action;
    private Request request;
    private Response response;
    private RequestLogger logger;

    @Before
    public void setUp() {
        action = new RedirectAction();
        request = new Request();
        response = new Response("HTTP/1.1");
        logger = new RequestLogger();
    }

    @Test
    public void runReturns302() throws IOException {
        request.setRequestLine("GET /redirect HTTP/1.1");
        request.setHeader("Host: localhost:5000");

        action.run(request, response, logger);

        assertEquals(Status.FOUND, response.getStatus());
        assertTrue(response.hasHeader("Location"));
    }

    @Test
    public void runAccessesHostHeader() throws IOException {
        request.setHeader("Host: localhost:5000");

        action.run(request, response, logger);

        assertEquals("localhost:5000", request.getHeaderValue(Header.HOST));
    }
}
