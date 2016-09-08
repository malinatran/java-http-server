package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class RedirectRouterCallbackTest {

    @Test
    public void runReturns302() {
        RouterCallback callback = new RedirectRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /redirect HTTP/1.1");
        request.setHeader("Host: localhost:5000");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.FOUND, response.getStatus());
    }

    @Test
    public void runAccessesHostHeader() {
        RouterCallback callback = new RedirectRouterCallback();
        Request request = new Request();
        request.setHeader("Host: localhost:5000");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals("localhost:5000", request.getHeaderValue("Host"));
    }
}