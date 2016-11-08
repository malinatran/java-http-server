package com.malinatran.router;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class EasterEggRouterCallbackTest {

    private RouterCallback callback;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        callback = new EasterEggRouterCallback();
        request = new Request();
        response = new Response("HTTP/1.1");
    }

    @Test
    public void runWithCoffeePathReturns418AndMessage() throws IOException {
        String TEAPOT_MESSAGE = "I'm a teapot";
        request.setRequestLine("GET /coffee HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.TEAPOT, response.getStatus());
        assertEquals(TEAPOT_MESSAGE, new String(response.getBodyContent()));
    }

    @Test
    public void runWithTeaPathReturns200() throws IOException {
        request.setRequestLine("GET /tea HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }
}