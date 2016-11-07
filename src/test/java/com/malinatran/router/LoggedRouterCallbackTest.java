package com.malinatran.router;

import com.malinatran.constant.Status;
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

    private String DEFAULT_DIRECTORY = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
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
        request.setDirectoryPath(DEFAULT_DIRECTORY);
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
        logger.setETagAndBody("ABCDEFGHIJK", new char[10]);

        loggedRouterCallback.run(request, response, logger);

        assertEquals("file1 contents", new String(response.getBodyContent()));
    }

    @Test
    public void runReturnsBodyForFormPath() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /form HTTP/1.1");
        char[] content = "testing".toCharArray();
        logger.setBody(content);

        loggedRouterCallback.run(request, response, logger);

        assertEquals("testing", new String(response.getBodyContent()));
    }

    @Test
    public void runReturnsPatchedContent() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        String hash = "a379624177abc4679cafafa8eae1d73e1478aaa6";
        String patched = "patched content";
        logger.setETagAndBody(hash, patched.toCharArray());

        loggedRouterCallback.run(request, response, logger);

        assertEquals(patched, new String(response.getBodyContent()));
    }
}