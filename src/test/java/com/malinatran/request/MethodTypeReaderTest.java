package com.malinatran.request;

import com.malinatran.setup.ServerSettings;
import com.malinatran.utility.RequestLogger;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class MethodTypeReaderTest {

    private String PATH = ServerSettings.HOME_DIRECTORY + ServerSettings.DEFAULT_DIRECTORY;
    private Request request;
    private RequestLogger logger;
    private boolean result;

    @Before
    public void setUp() {
        request = new Request();
        logger = new RequestLogger();
    }

    @Test
    public void isGetRequestWithLoggedBodyReturnsTrueIfGetRequestToForm() {
        request.setRequestLine("GET /form HTTP/1.1");

        result = MethodTypeReader.isGetRequestWithLoggedBody(request, logger);

        assertTrue(result);
    }

    @Test
    public void isGetRequestWithLoggedBodyReturnsTrueIfGetRequestToExistingFile() {
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

        result = MethodTypeReader.isGetRequestWithLoggedBody(secondRequest, logger);

        assertTrue(result);
    }

    @Test
    public void isPutOrPostToFormReturnsTrueIfPutFormPath() {
        result = MethodTypeReader.isPutOrPostToForm(Method.PUT, MethodTypeReader.FORM_PATH);

        assertTrue(result);
    }

    @Test
    public void isPutOrPostToFormReturnsTrueIfPostFormPath() {
        result = MethodTypeReader.isPutOrPostToForm(Method.POST, MethodTypeReader.FORM_PATH);

        assertTrue(result);
    }

    @Test
    public void isPutOrPostToFormReturnsFalse() {
        result = MethodTypeReader.isPutOrPostToForm("BOGUS", "/request");

        assertFalse(result);
    }

    @Test
    public void isDeleteToFormReturnsTrueIfDeleteFormPath() {
        result = MethodTypeReader.isDeleteToForm(Method.DELETE, MethodTypeReader.FORM_PATH);

        assertTrue(result);
    }

    @Test
    public void isDeleteToFormReturnsFalse() {
        result = MethodTypeReader.isDeleteToForm("BOGUS", "/request");

        assertFalse(result);
    }
}
