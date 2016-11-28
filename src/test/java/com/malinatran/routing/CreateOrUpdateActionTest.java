package com.malinatran.routing;

import com.malinatran.setup.ServerSettings;
import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;


import static org.junit.Assert.*;

public class CreateOrUpdateActionTest {

    private String PATH = ServerSettings.HOME_DIRECTORY + ServerSettings.DEFAULT_DIRECTORY;
    private Action action;
    private Request request;
    private Response response;
    private RequestLogger logger;

    @Before
    public void setUp() {
        action = new CreateOrUpdateAction();
        request = new Request();
        response = new Response("HTTP/1.1");
        logger = new RequestLogger();
    }

    @Test
    public void runWithBodyReturns200() throws IOException {
        request.setRequestLine("POST / HTTP/1.1");
        request.setDirectory(PATH);
        request.setBody(new char[4]);

        action.run(request, response, logger);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithoutBodyReturns404() throws IOException {
        request.setRequestLine("PUT / HTTP/1.1");
        request.setDirectory(PATH);

        action.run(request, response, logger);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithExistingResourceReturns405() throws IOException {
        request.setRequestLine("POST /text-file.txt HTTP/1.1");
        request.setDirectory(PATH);
        request.setBody(new char[5]);

        action.run(request, response, logger);

        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }
}
