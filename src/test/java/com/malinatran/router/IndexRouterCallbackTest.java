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
    public void runWithGetRequestToRootReturns200() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET / HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithGetRequestToExistingResourceAndValidFileFormatReturns200() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);
        String content = "file1 contents";

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
        assertEquals(content, response.getBodyContent().trim());
    }

    @Test
    public void runWithGetRequestToExistingResourceAndInvalidFormatReturns415() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /image.gif HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.UNSUPPORTED_MEDIA_TYPE, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndValidFileFormatReturns404() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /lala.txt HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndInvalidFileFormatReturns415() throws IOException {
        RouterCallback callback = new IndexRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /image.pdf HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.UNSUPPORTED_MEDIA_TYPE, response.getStatus());
    }
}