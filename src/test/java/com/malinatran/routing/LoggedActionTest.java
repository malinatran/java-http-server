package com.malinatran.routing;

import com.malinatran.setup.DirectoryArg;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.utility.RequestLogger;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.*;

public class LoggedActionTest {

    private String PATH = DirectoryArg.HOME_DIRECTORY + DirectoryArg.DEFAULT_DIRECTORY;
    private LoggedAction loggedAction;
    private RequestLogger logger;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        loggedAction = new LoggedAction();
        logger = new RequestLogger();
        request = new Request();
        request.setDirectory(PATH);
        response = new Response("HTTP/1.1");
    }

    @Test
    public void runReturns200AndGetsAllLoggedRequests() throws IOException {
        RequestLogger logger = new RequestLogger();
        Response response = new Response("HTTP/1.1");

        loggedAction.run(response, logger);

        assertEquals(Status.OK, response.getStatus());
        assertNotNull(response.getBodyContent());
    }

    @Test
    public void runReturnsOriginalContent() throws IOException {
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        request.setHeader("If-Match: random");

        loggedAction.run(request, response, logger);

        assertEquals("file1 contents\n", new String(response.getBodyContent()));
    }

    @Test
    public void runReturnsBodyForFormPath() throws IOException {
        Request initialRequest = new Request();
        char[] content = "testing".toCharArray();
        initialRequest.setRequestLine("POST /form HTTP/1.1");
        initialRequest.setBody(content);
        logger.logRequest(initialRequest);
        request.setRequestLine("GET /form HTTP/1.1");

        loggedAction.run(request, response, logger);

        assertEquals("testing", new String(response.getBodyContent()));
    }

    @Test
    public void runReturnsPatchedContent() throws IOException {
        String patched = "patched content";
        String hash = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
        request.setDirectory(PATH);
        request.setRequestLine("PATCH /patch-content.txt HTTP/1.1");
        request.setHeader("If-Match: " + hash);
        request.setBody(patched.toCharArray());
        logger.logRequest(request);
        Request secondRequest = new Request();
        secondRequest.setDirectory(PATH);
        secondRequest.setRequestLine("GET /patch-content.txt HTTP/1.1");
        secondRequest.setBody("default content".toCharArray());

        loggedAction.run(secondRequest, response, logger);

        assertEquals(patched, new String(response.getBodyContent()));
    }
}
