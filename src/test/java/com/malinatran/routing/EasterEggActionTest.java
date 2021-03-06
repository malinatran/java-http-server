package com.malinatran.routing;

import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;


import static org.junit.Assert.*;

public class EasterEggActionTest {

    private Action action;
    private Request request;
    private Response response;
    private RequestLogger logger;

    @Before
    public void setUp() {
        action = new EasterEggAction();
        request = new Request();
        response = new Response("HTTP/1.1");
        logger = new RequestLogger();
    }

    @Test
    public void runWithCoffeePathReturns418AndMessage() throws IOException {
        String TEAPOT_MESSAGE = "I'm a teapot";
        request.setRequestLine("GET /coffee HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.TEAPOT, response.getStatus());
        assertEquals(TEAPOT_MESSAGE, new String(response.getBodyContent()));
    }

    @Test
    public void runWithTeaPathReturns200() throws IOException {
        request.setRequestLine("GET /tea HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.OK, response.getStatus());
    }
}
