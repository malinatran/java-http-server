package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.setup.ServerSettings;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;

public class FileContentRouterCallbackTest {

    private String DEFAULT_PATH = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private RouterCallback callback;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        callback = new FileContentRouterCallback();
        request = new Request();
        response = new Response("HTTP/1.1");
    }

    @Test
    public void runWithGetRequestToExistingResourceAndValidTextFileReturns200() throws IOException {
        request.setRequestLine("GET /patch-content.txt HTTP/1.1");
        request.setDirectoryPath(DEFAULT_PATH);

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithGetRequestToValidImageFileReturns404() throws IOException {
        request.setRequestLine("GET /image.gif HTTP/1.1");
        request.setDirectoryPath(DEFAULT_PATH);

        callback.run(request, response);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndValidTextFileReturns404() throws IOException {
        request.setRequestLine("GET /lala.txt HTTP/1.1");
        request.setDirectoryPath(DEFAULT_PATH);

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndInvalidTextFileReturns404() throws IOException {
        request.setRequestLine("GET /image.pdf HTTP/1.1");
        request.setDirectoryPath(DEFAULT_PATH);

        callback.run(request, response);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithGetRequestToExistingResourceAndInvalidTextFileReturns415() throws IOException {
        File file = new File(DEFAULT_PATH + "exist.pdf");
        file.createNewFile();
        request.setRequestLine("GET /exist.pdf HTTP/1.1");
        request.setDirectoryPath(DEFAULT_PATH);

        callback.run(request, response);

        assertEquals(Status.UNSUPPORTED_MEDIA_TYPE, response.getStatus());
        assertTrue(file.exists());

        file.delete();
    }

    @Test
    public void RunWithGetRequestForPartialContentReturns206() throws IOException {
        request.setHeader("Range: bytes=0-4");
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        request.setDirectoryPath(DEFAULT_PATH);

        callback.run(request, response);

        assertEquals(Status.PARTIAL_CONTENT, response.getStatus());
    }
}
