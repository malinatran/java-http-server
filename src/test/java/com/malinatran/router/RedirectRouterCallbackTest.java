package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class RedirectRouterCallbackTest {

    private RouterCallback callback;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        callback = new RedirectRouterCallback();
        request = new Request();
        response = new Response("HTTP/1.1");
    }

    @Test
    public void runReturns302() throws IOException {
        request.setRequestLine("GET /redirect HTTP/1.1");
        request.setHeader("Host: localhost:5000");

        callback.run(request, response);

        assertEquals(Status.FOUND, response.getStatus());
    }

    @Test
    public void runAccessesHostHeader() throws IOException {
        request.setHeader("Host: localhost:5000");

        callback.run(request, response);

        assertEquals("localhost:5000", request.getHeaderValue("Host"));
    }
}