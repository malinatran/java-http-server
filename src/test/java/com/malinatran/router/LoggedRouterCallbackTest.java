package com.malinatran.router;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;
import com.malinatran.setup.ServerSettings;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class LoggedRouterCallbackTest {

    private String PATH = ServerSettings.ROOT + ServerSettings.DEFAULT_DIRECTORY;
    private Request request;
    private Response response;
    private RequestLogger logger;
    private LoggedRouterCallback loggedRouterCallback;

    @Before
    public void setUp() {
        request = new Request();
        response = new Response("HTTP/1.1");
        logger = new RequestLogger();
        loggedRouterCallback = new LoggedRouterCallback();
        request.setDirectoryPath(PATH);
    }

    @Test
    public void runReturns200AndGetsAllLoggedRequests() throws IOException {
        RequestLogger logger = new RequestLogger();
        Response response = new Response("HTTP/1.1");

        loggedRouterCallback.run(response, logger);

        assertEquals(Status.OK, response.getStatus());
        assertNotNull(response.getBodyContent());
    }

    @Test
    public void runReturnsOriginalContent() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        request.setHeader("If-Match: random");

        loggedRouterCallback.run(request, response, logger);

        assertEquals("file1 contents", new String(response.getBodyContent()));
    }

    @Test
    public void runReturnsBodyForFormPath() throws IOException, NoSuchAlgorithmException {
        Request initialRequest = new Request();
        char[] content = "testing".toCharArray();
        initialRequest.setRequestLine("POST /form HTTP/1.1");
        initialRequest.setBody(content);
        logger.logRequest(initialRequest);
        request.setRequestLine("GET /form HTTP/1.1");

        loggedRouterCallback.run(request, response, logger);

        assertEquals("testing", new String(response.getBodyContent()));
    }

    @Test
    public void runReturnsPatchedContent() throws IOException, NoSuchAlgorithmException {
        Request initialRequest = new Request();
        String hash = "a379624177abc4679cafafa8eae1d73e1478aaa6";
        String patched = "patched content";
        initialRequest.setRequestLine("PATCH /text-file.txt HTTP/1.1");
        initialRequest.setHeader("If-Match: " + hash);
        initialRequest.setBody(patched.toCharArray());
        logger.logRequest(initialRequest);
        request.setRequestLine("GET /text-file.txt HTTP/1.1");

        loggedRouterCallback.run(request, response, logger);

        assertEquals(patched, new String(response.getBodyContent()));
    }
}