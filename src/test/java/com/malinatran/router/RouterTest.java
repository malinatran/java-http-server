package com.malinatran.router;

import com.malinatran.mocks.MockRouter;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import com.malinatran.request.RequestLogger;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RouterTest {

    String responseOK = "HTTP/1.1 200 OK";
    String responseNotAllowed = "HTTP/1.1 405 Method Not Allowed";

    @Test
    public void addRouteStoresValueIntoHashMap() {
        Router router = new Router();

        router.addRoute("GET", "/malina", new IndexRouterCallback());

        assertTrue(router.hasRoute("GET /malina"));
    }

    @Test
    public void getResponseForGetReturns200() throws IOException {
        Request request = new Request();
        RequestLogger requestLogger = new RequestLogger();
        request.setRequestLine("GET / HTTP/1.1");
        request.setBody("my=data");
        Router mockRouter = new MockRouter();

        Response response = mockRouter.getResponse(request, requestLogger);

        assertEquals(responseOK + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForBogusReturns405() throws IOException {
        Request request = new Request();
        RequestLogger requestLogger = new RequestLogger();
        request.setRequestLine("BOGUS /file1 HTTP/1.1");
        Router router = new Router();

        Response response = router.getResponse(request, requestLogger);

        assertEquals(responseNotAllowed + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForGetWithRandomPathReturns200() throws IOException {
        Request request = new Request();
        RequestLogger requestLogger = new RequestLogger();
        request.setRequestLine("GET /file1 HTTP/1.1");
        Router mockRouter = new MockRouter();

        Response response = mockRouter.getResponse(request, requestLogger);

        assertEquals(responseOK + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForPutWithRandomPathReturns405() throws IOException {
        Request request = new Request();
        RequestLogger requestLogger = new RequestLogger();
        request.setRequestLine("PUT /file1 HTTP/1.1");
        Router router = new Router();

        Response response = router.getResponse(request, requestLogger);

        assertEquals(responseNotAllowed + "\r\n", response.getStatusLine());
    }

    @Test
    public void getResponseForPostWithRandomPathReturns405() throws IOException {
        Request request = new Request();
        RequestLogger requestLogger = new RequestLogger();
        request.setRequestLine("POST /hello HTTP/1.1");
        Router router = new Router();

        Response response = router.getResponse(request, requestLogger);

        assertEquals(responseNotAllowed + "\r\n", response.getStatusLine());
    }
}