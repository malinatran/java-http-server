package com.malinatran.router;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class FormRouterCallbackTest {

    private Request request;
    private Response response;
    private RouterCallback callback;

    @Before
    public void setUp() {
        request = new Request();
        response = new Response("HTTP/1.1");
        callback = new FormRouterCallback();
    }

    @Test
    public void runWithGetReturns200() throws IOException {
        request.setRequestLine("GET /form HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }
}