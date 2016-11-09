package com.malinatran.routing;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class NotFoundOrAllowedActionTest {

    private Action action;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        action = new NotFoundOrAllowedAction();
        request = new Request();
        response = new Response("HTTP/1.1");
    }

    @Test
    public void runWithHeadReturns404() throws IOException {
        request.setRequestLine("HEAD /file1 HTTP/1.1");

        action.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithPutReturns405() throws IOException {
        request.setRequestLine("PUT /file1 HTTP/1.1");

        action.run(request, response);

        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }
}