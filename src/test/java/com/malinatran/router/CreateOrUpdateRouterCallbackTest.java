package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class CreateOrUpdateRouterCallbackTest {

    @Test
    public void testRunWithBody() {
        RouterCallback callback = new CreateOrUpdateRouterCallback();
        Request request = new Request();
        request.setRequestLine("POST / HTTP/1.1");
        request.setBody("Get down with the get down");
        Response response = new Response("POST", "HTTP/1.1", "/", "Get down with the get down");
        callback.run(request, response);
        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void testRunWithoutBody() {
        RouterCallback callback = new CreateOrUpdateRouterCallback();
        Request request = new Request();
        request.setRequestLine("PUT / HTTP/1.1");
        Response response = new Response("PUT", "HTTP/1.1", "/", null);
        callback.run(request, response);
        assertEquals(Status.NOT_FOUND, response.getStatus());
    }
}