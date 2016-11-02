package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class LogsRouterCallbackTest {

    @Test
    public void runWithInvalidCredentialsReturns401() throws IOException {
        RouterCallback callback = new LogsRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic HelloWorld");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.UNAUTHORIZED, response.getStatus());
    }
}