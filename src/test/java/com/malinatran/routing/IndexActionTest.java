package com.malinatran.routing;

import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import java.io.IOException;


import static org.junit.Assert.*;

public class IndexActionTest {

    @Test
    public void runWithGetRequestToRootReturns200() throws IOException {
        Action action = new IndexAction();
        Request request = new Request();
        request.setRequestLine("GET / HTTP/1.1");
        request.setDirectory("/test/directory/");
        Response response = new Response("HTTP/1.1");
        RequestLogger logger = new RequestLogger();

        action.run(request, response, logger);

        assertEquals(Status.OK, response.getStatus());
    }
}
