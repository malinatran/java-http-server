package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class LogsRouterCallbackTest {
//
    @Test
    public void testRunForValidCredentials() {
        RouterCallback callback = new LogsRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic YWRtaW46aHVudGVyMg==");
        Response response = new Response("GET", "HTTP/1.1", "/logs", null);
        callback.run(request, response);
        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void testRunForInvalidCredentials() {
        RouterCallback callback = new LogsRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic HelloWorld");
        Response response = new Response("GET", "HTTP/1.1", "/logs", null);
        callback.run(request, response);
        assertEquals(Status.UNAUTHORIZED, response.getStatus());
    }
}