package com.malinatran.router;

import com.malinatran.mocks.MockRouter;
import com.malinatran.request.Request;
import com.malinatran.request.RequestLogger;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    private String responseOK = "HTTP/1.1 200 OK";
    private String responseNotAllowed = "HTTP/1.1 405 Method Not Allowed";
    private Router router;
    private Router mockRouter;
    private Request request;
    private Request subsequentRequest;
    private Response response;
    private RequestLogger logger;

    @Before
    public void setUp() {
        router = new Router();
        mockRouter = new MockRouter();
        request = new Request();
        subsequentRequest = new Request();
        response = new Response("HTTP/1.1");
        logger = new RequestLogger();
    }

    @Test
    public void addRouteStoresValueIntoHashMap() {
        router.addRoute("GET", "/malina", new IndexRouterCallback());

        assertTrue(router.hasRoute("GET /malina"));
    }

    @Test
    public void getResponseForGetReturns200() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET / HTTP/1.1");
        request.setBody(new char[10]);

        response = mockRouter.getResponse(request, logger);

        assertEquals(responseOK + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForBogusReturns405() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("BOGUS /file1 HTTP/1.1");

        response = router.getResponse(request, logger);

        assertEquals(responseNotAllowed + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForGetWithRandomPathReturns200() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /file1 HTTP/1.1");

        response = mockRouter.getResponse(request, logger);

        assertEquals(responseOK + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForPutWithRandomPathReturns405() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("PUT /file1 HTTP/1.1");

        response = router.getResponse(request, logger);

        assertEquals(responseNotAllowed + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForPostWithRandomPathReturns405() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("POST /hello HTTP/1.1");

        response = router.getResponse(request, logger);

        assertEquals(responseNotAllowed + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForPostWithFormPathReturns200() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("GET /form HTTP/1.1");
        request.setBody(new char[10]);

        response = router.getResponse(request, logger);

        assertEquals(responseOK + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForGetWithFormPathAfterDeleteReturns200AndSetsBodyToBeEmpty() throws IOException, NoSuchAlgorithmException {
        request.setRequestLine("DELETE /form HTTP/1.1");
        subsequentRequest.setRequestLine("GET /form HTTP/1.1");

        response = router.getResponse(subsequentRequest, logger);

        assertEquals(responseOK + "\r\n", response.getStatusLine());
        assertEquals("", new String(response.getBodyContent()));
    }
}