package com.malinatran.routing;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OptionsActionTest {

    @Test
    public void runWithMethodOptionsPathAndOptionsMethodReturns200AndAllowHeader() throws IOException {
        Action action = new OptionsAction();
        Request request = new Request();
        request.setRequestLine("OPTIONS /method_options HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        action.run(request, response);

        assertEquals(Status.OK, response.getStatus());
        assertTrue(response.hasHeader(Header.ALLOW));
    }
}