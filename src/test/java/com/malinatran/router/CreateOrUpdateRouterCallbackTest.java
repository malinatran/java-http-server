package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class CreateOrUpdateRouterCallbackTest {

    private static final String BODY_MESSAGE = "Get down with the get down";

    @Test
    public void testRunWithBody() {
        RouterCallback callback = new CreateOrUpdateRouterCallback();
        Request request = new Request();
        request.setRequestLine("POST / HTTP/1.1");
        request.setBody(BODY_MESSAGE);
        Response response = new Response(Method.POST, "HTTP/1.1", "/", BODY_MESSAGE);

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