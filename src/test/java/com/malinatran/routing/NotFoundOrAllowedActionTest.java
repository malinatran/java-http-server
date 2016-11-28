package com.malinatran.routing;

import com.malinatran.utility.RequestLogger;
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
    private RequestLogger logger;

    @Before
    public void setUp() {
        action = new NotFoundOrAllowedAction();
        request = new Request();
        response = new Response("HTTP/1.1");
        logger = new RequestLogger();
    }

    @Test
    public void runWithHeadReturns404() throws IOException {
        request.setRequestLine("HEAD /file1 HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithPutReturns405() throws IOException {
        request.setRequestLine("PUT /file1 HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }
}
