package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class IndexRouterCallbackTest {

    @Test
    public void testRun() {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET / HTTP/1.1");
        Response response = new Response("GET", "HTTP/1.1", "/", null);

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }
}