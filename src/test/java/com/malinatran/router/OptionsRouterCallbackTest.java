package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class OptionsRouterCallbackTest {

    @Test
    public void testRun() {
        RouterCallback callback = new OptionsRouterCallback();
        Request request = new Request();
        request.setRequestLine("OPTIONS /method_options HTTP/1.1");
        Response response = new Response("OPTIONS", "HTTP/1.1", "/method_options", null);
        callback.run(request, response);
        assertEquals(Status.OK, response.getStatus());
        assertTrue(response.hasHeader("Allow"));
    }

}