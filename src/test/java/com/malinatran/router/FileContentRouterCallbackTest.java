package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class FileContentRouterCallbackTest {

    @Test
    public void runWithGetRequestToExistentResourceAndValidTextFiletReturns200() throws IOException {
        RouterCallback callback = new FileContentRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /patch-content.txt HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithGetRequestToValidImageFileReturns404() throws IOException {
        RouterCallback callback = new FileContentRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /image.gif HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndValidTextFileReturns404() throws IOException {
        RouterCallback callback = new FileContentRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /lala.txt HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndInvalidTextFileReturns415() throws IOException {
        RouterCallback callback = new FileContentRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /image.pdf HTTP/1.1");
        Response response = new Response("HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.UNSUPPORTED_MEDIA_TYPE, response.getStatus());
    }
}
