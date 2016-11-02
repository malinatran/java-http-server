package com.malinatran.router;

import com.malinatran.setup.ServerSettings;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class CreateOrUpdateRouterCallbackTest {

    private String DEFAULT_DIRECTORY = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private RouterCallback callback;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        callback = new CreateOrUpdateRouterCallback();
        request = new Request();
        response = new Response("HTTP/1.1");
    }

    @Test
    public void runWithBodyReturns200() throws IOException {
        request.setRequestLine("POST / HTTP/1.1");
        request.setDirectoryPath(DEFAULT_DIRECTORY);
        request.setBody("Testing");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithoutBodyReturns404() throws IOException {
        request.setRequestLine("PUT / HTTP/1.1");
        request.setDirectoryPath(DEFAULT_DIRECTORY);

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithExistingResourceReturns405() throws IOException {
        request.setRequestLine("POST /text-file.txt HTTP/1.1");
        request.setDirectoryPath(DEFAULT_DIRECTORY);
        request.setBody("Testing");

        callback.run(request, response);

        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }
}