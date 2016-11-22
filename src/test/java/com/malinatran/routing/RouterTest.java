package com.malinatran.routing;

import com.malinatran.mocks.MockRouter;
import com.malinatran.request.Request;
import com.malinatran.setup.ServerSettings;
import com.malinatran.utility.RequestLogger;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.malinatran.response.Formatter.CRLF;
import static org.junit.Assert.assertEquals;

public class RouterTest {

    private String PATH = ServerSettings.HOME_DIRECTORY + ServerSettings.DEFAULT_DIRECTORY;
    private String responseNotAllowed = "HTTP/1.1 405 Method Not Allowed";
    private String responseNotFound = "HTTP/1.1 404 Not Found";
    private String responseOK = "HTTP/1.1 200 OK";
    private RequestLogger logger;
    private Router mockRouter;
    private Request request;
    private Response response;
    private Router router;
    private Request subsequentRequest;

    @Before
    public void setUp() {
        logger = new RequestLogger();
        mockRouter = new MockRouter();
        request = new Request();
        request.setDirectory(PATH);
        response = new Response("HTTP/1.1");
        router = new Router();
        subsequentRequest = new Request();
    }

    @Test
    public void getResponseForGetReturns200() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET / HTTP/1.1");
        request.setBody(new char[10]);

        response = mockRouter.getResponse(request, logger);

        assertEquals(responseOK + CRLF, response.getStatusLine());
    }

    @Test
    public void getResponseForBogusReturns405() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("BOGUS /file1 HTTP/1.1");

        response = router.getResponse(request, logger);

        assertEquals(responseNotAllowed + CRLF, response.getStatusLine());
    }

    @Test
    public void getResponseForGetWithRandomPathReturns200() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /file1 HTTP/1.1");

        response = mockRouter.getResponse(request, logger);

        assertEquals(responseOK + CRLF, response.getStatusLine());
    }

    @Test
    public void getResponseForPutWithRandomPathReturns405() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("PUT /lala HTTP/1.1");

        response = router.getResponse(request, logger);

        assertEquals(responseNotFound + CRLF, response.getStatusLine());
    }

    @Test
    public void getResponseForPostWithRandomPathReturns405() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("POST /hello HTTP/1.1");

        response = router.getResponse(request, logger);

        assertEquals(responseNotFound + CRLF, response.getStatusLine());
    }

    @Test
    public void getResponseForPostWithFormPathReturns200() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /form HTTP/1.1");
        request.setBody(new char[10]);

        response = router.getResponse(request, logger);

        assertEquals(responseOK + CRLF, response.getStatusLine());
    }

    @Test
    public void getResponseForGetWithFormPathAfterDeleteReturns200AndSetsBodyToBeEmpty() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("DELETE /form HTTP/1.1");
        subsequentRequest.setRequestLine("GET /form HTTP/1.1");

        response = router.getResponse(subsequentRequest, logger);

        assertEquals(responseOK + CRLF, response.getStatusLine());
        assertEquals("", new String(response.getBodyContent()));
    }
}