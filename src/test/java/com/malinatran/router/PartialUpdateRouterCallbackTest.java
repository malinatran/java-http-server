package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.setup.ServerSettings;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PartialUpdateRouterCallbackTest {

    private String DEFAULT_DIRECTORY = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private RouterCallback callback;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        callback = new PartialUpdateRouterCallback();
        request = new Request();
        response = new Response("HTTP/1.1");
    }

    @Test
    public void runWithPatchReturns206() throws IOException {
        request.setDirectoryPath(DEFAULT_DIRECTORY);
        request.setRequestLine("PATCH /patch-content.txt HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.NO_CONTENT, response.getStatus());
    }
}