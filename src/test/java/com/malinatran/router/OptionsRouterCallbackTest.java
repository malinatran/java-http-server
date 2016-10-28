package com.malinatran.router;

import com.malinatran.constants.Header;
import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class OptionsRouterCallbackTest {

    @Test
    public void runWithMethodOptionsPathAndOptionsMethodReturns200AndAllowHeader() throws IOException {
        RouterCallback callback = new OptionsRouterCallback();
        Request request = new Request();
        request.setRequestLine("OPTIONS /method_options HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
        assertTrue(response.hasHeader(Header.ALLOW));
    }

}