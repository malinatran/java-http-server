package com.malinatran.router;

import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class NotFoundOrAllowedRouterCallbackTest {

    private RouterCallback callback;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        callback = new NotFoundOrAllowedRouterCallback();
        request = new Request();
        response = new Response("HTTP/1.1");
    }

    @Test
    public void runWithHeadReturns404() throws IOException {
        request.setRequestLine("HEAD /file1 HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithPutReturns405() throws IOException {
        request.setRequestLine("PUT /file1 HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }
}