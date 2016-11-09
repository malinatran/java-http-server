package com.malinatran.routing;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class FormActionTest {

    private Request request;
    private Response response;
    private Action action;

    @Before
    public void setUp() {
        request = new Request();
        response = new Response("HTTP/1.1");
        action = new FormAction();
    }

    @Test
    public void runWithGetReturns200() throws IOException {
        request.setRequestLine("GET /form HTTP/1.1");

        action.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }
}