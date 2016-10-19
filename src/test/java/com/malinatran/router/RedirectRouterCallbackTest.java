package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RedirectRouterCallbackTest {

    @Test
    public void runReturns302() throws IOException {
        RouterCallback callback = new RedirectRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /redirect HTTP/1.1");
        request.setHeader("Host: localhost:5000");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.FOUND, response.getStatus());
    }

    @Test
    public void runAccessesHostHeader() throws IOException {
        RouterCallback callback = new RedirectRouterCallback();
        Request request = new Request();
        request.setHeader("Host: localhost:5000");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals("localhost:5000", request.getHeaderValue("Host"));
    }
}