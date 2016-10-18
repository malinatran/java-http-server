package com.malinatran.router;

import com.malinatran.mocks.MockRouter;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RouterTest {

    private String responseOK = "HTTP/1.1 200 OK";
    private String responseNotAllowed = "HTTP/1.1 405 Method Not Allowed";

    @Test
    public void addRouteStoresValueIntoHashMap() {
        Router router = new Router();

        router.addRoute("GET", "/malina", new IndexRouterCallback());

        assertTrue(router.hasRoute("GET /malina"));
    }

    @Test
    public void getResponseForGetReturns200() throws IOException {
        Request request = new Request();
        Logger logger = new Logger();
        request.setRequestLine("GET / HTTP/1.1");
        request.setBody("my=data");
        Router mockRouter = new MockRouter();

        Response response = mockRouter.getResponse(request, logger);

        assertEquals(responseOK, response.getStatusLine());
    }

    @Test
    public void getResponseForBogusReturns405() throws IOException {
        Request request = new Request();
        Logger logger = new Logger();
        request.setRequestLine("BOGUS /file1 HTTP/1.1");
        Router router = new Router();

        Response response = router.getResponse(request, logger);

        assertEquals(responseNotAllowed, response.getStatusLine());
    }

    @Test
    public void getResponseForGetWithRandomPathReturns200() throws IOException {
        Request request = new Request();
        Logger logger = new Logger();
        request.setRequestLine("GET /file1 HTTP/1.1");
        Router mockRouter = new MockRouter();

        Response response = mockRouter.getResponse(request, logger);

        assertEquals(responseOK, response.getStatusLine());
    }

    @Test
    public void getResponseForPutWithRandomPathReturns405() throws IOException {
        Request request = new Request();
        Logger logger = new Logger();
        request.setRequestLine("PUT /file1 HTTP/1.1");
        Router router = new Router();

        Response response = router.getResponse(request, logger);

        assertEquals(responseNotAllowed, response.getStatusLine());
    }

    @Test
    public void getResponseForPostWithRandomPathReturns405() throws IOException {
        Request request = new Request();
        Logger logger = new Logger();
        request.setRequestLine("POST /hello HTTP/1.1");
        Router router = new Router();

        Response response = router.getResponse(request, logger);

        assertEquals(responseNotAllowed, response.getStatusLine());
    }
}