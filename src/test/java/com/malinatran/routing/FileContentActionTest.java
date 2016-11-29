package com.malinatran.routing;

import com.malinatran.setup.DirectoryArg;
import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileContentActionTest {

    private String PATH = DirectoryArg.HOME_DIRECTORY + DirectoryArg.DEFAULT_DIRECTORY;
    private Action action;
    private Request request;
    private Response response;
    private RequestLogger logger;

    @Before
    public void setUp() {
        action = new FileContentAction();
        request = new Request();
        request.setDirectory(PATH);
        response = new Response("HTTP/1.1");
        logger = new RequestLogger();
    }

    @Test
    public void runWithGetRequestToExistingResourceAndValidTextFileReturns200() throws IOException {
        request.setRequestLine("GET /patch-content.txt HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithGetRequestToValidImageFileReturns404() throws IOException {
        request.setRequestLine("GET /image.gif HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndValidTextFileReturns404() throws IOException {
        request.setRequestLine("GET /lala.txt HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithGetRequestToNonexistentResourceAndInvalidTextFileReturns404() throws IOException {
        request.setRequestLine("GET /image.pdf HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void runWithGetRequestToExistingResourceAndInvalidTextFileReturns415() throws IOException {
        File file = new File(PATH + "exist.pdf");
        file.delete();
        file.createNewFile();
        request.setRequestLine("GET /exist.pdf HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.UNSUPPORTED_MEDIA_TYPE, response.getStatus());
        assertTrue(file.exists());

        file.delete();
    }

    @Test
    public void runWithGetRequestForPartialContentReturns206() throws IOException {
        request.setHeader("Range: bytes=0-4");
        request.setRequestLine("GET /text-file.txt HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.PARTIAL_CONTENT, response.getStatus());
    }

    @Test
    public void runReturns200AndSetsContentTypeAsHeader() throws IOException {
        String text = "file1 contents\n";
        request.setRequestLine("GET /text-file.txt HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.OK, response.getStatus());
        assertEquals(text, new String(response.getBodyContent()));
        assertTrue(response.hasHeader(Header.CONTENT_TYPE));
    }

    @Test
    public void runSetsContentRangeAsHeader() throws IOException {
        String text = "ile1 ";
        request.setHeader("Range: bytes=1-5");
        request.setRequestLine("GET /text-file.txt HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(text, new String(response.getBodyContent()));
        assertTrue(response.hasHeader(Header.CONTENT_RANGE));
    }

    @Test
    public void runReturns200AndSetsContentLengthAndTypeAsHeaders() throws IOException {
        request.setRequestLine("GET /image.jpeg HTTP/1.1");

        action.run(request, response, logger);

        assertEquals(Status.OK, response.getStatus());
        assertTrue(response.hasHeader(Header.CONTENT_TYPE));
        assertTrue(response.hasHeader(Header.CONTENT_LENGTH));
    }
}
