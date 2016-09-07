package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class LogsRouterCallbackTest {

    @Test
    public void runWithValidCredentialsReturns200() {
        RouterCallback callback = new LogsRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic YWRtaW46aHVudGVyMg==");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithInvalidCredentialsReturns401() {
        RouterCallback callback = new LogsRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic HelloWorld");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.UNAUTHORIZED, response.getStatus());
    }
}