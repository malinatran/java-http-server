package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class RedirectRouterCallbackTest {

    @Test
    public void testRun() {
        RouterCallback callback = new RedirectRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /redirect HTTP/1.1");
        Response response = new Response("GET", "HTTP/1.1", "/redirect", null);
        callback.run(request, response);
        assertEquals(Status.FOUND, response.getStatus());
    }
}