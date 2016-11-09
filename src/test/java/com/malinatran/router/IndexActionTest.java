package com.malinatran.router;

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
        request.setDirectoryPath("/test/directory/");
        Response response = new Response("HTTP/1.1");

        action.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }
}