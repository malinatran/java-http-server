package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class IndexRouterCallbackTest {

    @Test
    public void runReturns200IfGETRequestToRootRoute() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET / HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runReturns200IfGETRequestToExistingResourceWithValidFileFormat() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);
        String content = "This is a file that contains text to read part of in order to fulfill a 206.\n";

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
//        assertEquals(content, response.getBodyContent());
    }

    @Test
    public void runReturns415IfGETRequestToExistingResourceWithInvalidFileFormat() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /image.gif HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.UNSUPPORTED_MEDIA_TYPE, response.getStatus());
    }

    @Test
    public void runReturns404IfGETRequestToNonexistentResourceAndValidFileFormat() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /lala.txt HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runReturns415IfGETRequestToNonexistentResourceAndInvalidFileFormat() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /image.pdf HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.UNSUPPORTED_MEDIA_TYPE, response.getStatus());
    }
}