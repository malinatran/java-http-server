package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CreateOrUpdateRouterCallbackTest {

    String DEFAULT_PATH = System.getProperty("user.home") + "/Development/cob_spec/public/";

    @Test
    public void runWithBodyReturns200() throws IOException {
        RouterCallback callback = new CreateOrUpdateRouterCallback();
        Request request = new Request();
        request.setRequestLine("POST / HTTP/1.1");
        request.setDirectoryPath(DEFAULT_PATH);
        request.setBody("Testing");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithoutBodyReturns404() throws IOException {
        RouterCallback callback = new CreateOrUpdateRouterCallback();
        Request request = new Request();
        request.setRequestLine("PUT / HTTP/1.1");
        request.setDirectoryPath(DEFAULT_PATH);
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithExistingResourceReturns405() throws IOException {
        RouterCallback callback = new CreateOrUpdateRouterCallback();
        Request request = new Request();
        request.setRequestLine("POST /text-file.txt HTTP/1.1");
        request.setDirectoryPath(DEFAULT_PATH);
        request.setBody("Testing");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }
}