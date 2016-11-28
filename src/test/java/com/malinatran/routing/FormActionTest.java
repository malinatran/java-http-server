package com.malinatran.routing;

import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;


import static org.junit.Assert.*;

public class FormActionTest {

    @Test
    public void runWithGetReturns200() throws IOException {

        Request request = new Request();
        Response response = new Response("HTTP/1.1");
        Action action = new FormAction();
        RequestLogger logger = new RequestLogger();
        request.setRequestLine("GET /form HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.OK, response.getStatus());
    }
}
