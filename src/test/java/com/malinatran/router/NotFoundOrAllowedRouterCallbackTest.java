package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class NotFoundOrAllowedRouterCallbackTest {

    @Test
    public void runWithHeadReturns404() throws IOException {
        RouterCallback callback = new NotFoundOrAllowedRouterCallback();
        Request request = new Request();
        request.setRequestLine("HEAD /file1 HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithPutReturns405() throws IOException {
        RouterCallback callback = new NotFoundOrAllowedRouterCallback();
        Request request = new Request();
        request.setRequestLine("PUT /file1 HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }
}