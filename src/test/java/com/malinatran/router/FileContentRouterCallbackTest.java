package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class FileContentRouterCallbackTest {

    @Test
    public void runWithGetRequestToExistingResourceAndValidFileFormatReturns200() throws IOException {
        RouterCallback callback = new FileContentRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);
        String content = "file1 contents";

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
        assertEquals(content, response.getBodyContent().trim());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndValidFileFormatReturns404() throws IOException {
        RouterCallback callback = new FileContentRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /lala.txt HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndInvalidFileFormatReturns415() throws IOException {
        RouterCallback callback = new FileContentRouterCallback();
        Request request = new Request();
        request.setRequestLine("GET /image.pdf HTTP/1.1");
        Response response = new Response("HTTP/1.1", null);

        callback.run(request, response);

        assertEquals(Status.UNSUPPORTED_MEDIA_TYPE, response.getStatus());
    }
}
