package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class EasterEggRouterCallbackTest {

    @Test
    public void runWithCoffeePathReturns418AndMessage() throws IOException {
        String TEAPOT_MESSAGE = "\r\nI'm a teapot\r\n";
        RouterCallback callback = new EasterEggRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /coffee HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.TEAPOT, response.getStatus());
        assertEquals(TEAPOT_MESSAGE, response.getBodyContent());
    }

    @Test
    public void runWithTeaPathReturns200() throws IOException {
        RouterCallback callback = new EasterEggRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /tea HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }
}