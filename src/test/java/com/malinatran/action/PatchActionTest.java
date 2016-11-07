package com.malinatran.action;

import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.setup.ServerSettings;
import com.malinatran.request.RequestLogger;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class PatchActionTest {

    private String DEFAULT_DIRECTORY = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private Request request;
    private Response response;
    private RequestLogger logger;
    private PatchAction patchAction;

    @Before
    public void setUp() {
        request = new Request();
        response = new Response("HTTP/1.1");
        logger = new RequestLogger();
        patchAction = new PatchAction();
        request.setDirectoryPath(DEFAULT_DIRECTORY);
    }

    @Test
    public void setBodyReturnsOriginalContent() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        logger.setETagAndBody("ABCDEFGHIJK", new char[10]);

        patchAction.setBody(request, response, logger);

        assertEquals("file1 contents", new String(response.getBodyContent()));
    }

    @Test
    public void setBodyReturnsBodyForFormPath() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /form HTTP/1.1");
        char[] content = "testing".toCharArray();
        logger.setBody(content);

        patchAction.setBody(request, response, logger);

        assertEquals("testing", new String(response.getBodyContent()));
    }

    @Test
    public void setBodyReturnsPatchedContent() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        String hash = "a379624177abc4679cafafa8eae1d73e1478aaa6";
        String patched = "patched content";
        logger.setETagAndBody(hash, patched.toCharArray());

        patchAction.setBody(request, response, logger);

        assertEquals(patched, new String(response.getBodyContent()));
    }
}