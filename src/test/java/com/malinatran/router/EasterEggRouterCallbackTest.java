package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class EasterEggRouterCallbackTest {

    private static final String TEAPOT_MESSAGE = "\r\nI'm a teapot\r\n";

    @Test
    public void testRunCoffee() {
        RouterCallback callback = new EasterEggRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /coffee HTTP/1.1");
        Response response = new Response(Method.GET, "HTTP/1.1", "/coffee");

        callback.run(request, response);

        assertEquals(Status.TEAPOT, response.getStatus());
        assertEquals(TEAPOT_MESSAGE, response.getBodyContent());
    }

    @Test
    public void testRunTea() {
        RouterCallback callback = new EasterEggRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /tea HTTP/1.1");
        Response response = new Response(Method.GET, "HTTP/1.1", "/tea");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }
}