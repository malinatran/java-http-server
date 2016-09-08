package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class CreateOrUpdateRouterCallbackTest {

    @Test
    public void runWithBodyReturns200() {
        String BODY_MESSAGE = "Get down with the get down";
        RouterCallback callback = new CreateOrUpdateRouterCallback();
        Request request = new Request();
        request.setRequestLine("POST / HTTP/1.1");
        request.setBody(BODY_MESSAGE);
        Response response = new Response("HTTP/1.1", BODY_MESSAGE);

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithoutBodyReturns404() {
        RouterCallback callback = new CreateOrUpdateRouterCallback();
        Request request = new Request();
        request.setRequestLine("PUT / HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }
}