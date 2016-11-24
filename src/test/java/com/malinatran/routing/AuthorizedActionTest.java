package com.malinatran.routing;

import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import java.io.IOException;


import static org.junit.Assert.assertEquals;

public class AuthorizedActionTest {

    @Test
    public void runWithInvalidCredentialsReturns401() throws IOException {
        Action action = new AuthorizedAction();
        Request request = new Request();
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic HelloWorld");
        Response response = new Response("HTTP/1.1");
        RequestLogger logger = new RequestLogger();

        action.run(request, response, logger);

        assertEquals(Status.UNAUTHORIZED, response.getStatus());
    }
}
