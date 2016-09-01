package com.malinatran.router;

import com.malinatran.mocks.MockRouter;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class RouterTest {

    private String responseOK = "HTTP/1.1 200 OK";

    @Test
    public void testAddRoute() {
        Router router = new Router();
        router.addRoute("GET", "/malina", new IndexRouterCallback());
        assertTrue(router.hasRoute("GET /malina"));
    }

    @Test
    public void testGetResponse() {
        Request request = new Request();
        request.setRequestLine("GET / HTTP/1.1");
        request.setBody("my=data");
        Router mockRouter = new MockRouter();
        Response response = mockRouter.getResponse(request);
        assertEquals(responseOK, response.getStatusLine());
    }
}