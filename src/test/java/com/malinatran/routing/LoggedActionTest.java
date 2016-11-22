package com.malinatran.routing;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.utility.RequestLogger;
import com.malinatran.setup.ServerSettings;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class LoggedActionTest {

    private String PATH = ServerSettings.HOME_DIRECTORY + ServerSettings.DEFAULT_DIRECTORY;
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
    public void runReturnsOriginalContent() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        request.setHeader("If-Match: random");

        loggedAction.run(request, response, logger);

        assertEquals("file1 contents\n", new String(response.getBodyContent()));
    }

    @Test
    public void runReturnsBodyForFormPath() throws IOException, NoSuchAlgorithmException {
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
    public void runReturnsPatchedContent() throws IOException, NoSuchAlgorithmException {
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