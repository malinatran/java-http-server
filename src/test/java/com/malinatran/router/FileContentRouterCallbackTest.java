package com.malinatran.router;

import com.malinatran.utility.Header;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Image;
import com.malinatran.response.Response;
import com.malinatran.setup.ServerSettings;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;

public class FileContentRouterCallbackTest {

    private String PATH = ServerSettings.HOME_DIRECTORY + ServerSettings.DEFAULT_DIRECTORY;
    private RouterCallback callback;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        callback = new FileContentRouterCallback();
        request = new Request();
        request.setDirectoryPath(PATH);
        response = new Response("HTTP/1.1");
    }

    @Test
    public void runWithGetRequestToExistingResourceAndValidTextFileReturns200() throws IOException {
        request.setRequestLine("GET /patch-content.txt HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithGetRequestToValidImageFileReturns404() throws IOException {
        request.setRequestLine("GET /image.gif HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndValidTextFileReturns404() throws IOException {
        request.setRequestLine("GET /lala.txt HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndInvalidTextFileReturns404() throws IOException {
        request.setRequestLine("GET /image.pdf HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithGetRequestToExistingResourceAndInvalidTextFileReturns415() throws IOException {
        File file = new File(PATH + "exist.pdf");
        file.delete();
        file.createNewFile();
        request.setRequestLine("GET /exist.pdf HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.UNSUPPORTED_MEDIA_TYPE, response.getStatus());
        assertTrue(file.exists());

        file.delete();
    }

    @Test
    public void runWithGetRequestForPartialContentReturns206() throws IOException {
        request.setHeader("Range: bytes=0-4");
        request.setRequestLine("GET /text-file.txt HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.PARTIAL_CONTENT, response.getStatus());
    }

    @Test
    public void runReturns200AndSetsContentTypeAsHeader() throws IOException {
        String text = "file1 contents";
        request.setRequestLine("GET /text-file.txt HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
        assertEquals(text, new String(response.getBodyContent()));
        assertTrue(response.hasHeader(Header.CONTENT_TYPE));
    }

    @Test
    public void runSetsContentRangeAsHeader() throws IOException {
        String text = "ile1 ";
        request.setHeader("Range: bytes=1-5");
        request.setRequestLine("GET /text-file.txt HTTP/1.1");

        callback.run(request, response);

        assertEquals(text, new String(response.getBodyContent()));
        assertTrue(response.hasHeader(Header.CONTENT_RANGE));
    }

    @Test
    public void runReturns200AndSetsContentLengthAndTypeAsHeaders() throws IOException {
        request.setRequestLine("GET /image.jpeg HTTP/1.1");

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
        assertTrue(response.hasHeader(Header.CONTENT_TYPE));
        assertTrue(response.hasHeader(Header.CONTENT_LENGTH));
    }
}
