package com.malinatran;

import static org.junit.Assert.*;

public class ResponseTest {
    @org.junit.Test

    public void testGetResponseForGET() {
        Request request = new Request();
        request.setRequestLine("GET / HTTP/1.1");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(StatusCodes.OK, message);
    }

    public void testGetResponseForHEAD() {
        Request request = new Request();
        request.setRequestLine("HEAD / HTTP/1.1");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(StatusCodes.OK, message);
    }

    public void testGetResponseForHEADWithURI() {
        Request request = new Request();
        request.setRequestLine("HEAD /foobar HTTP/1.1");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(StatusCodes.NOT_FOUND, message);
    }

    public void testGetResponseForPUT() {
        Request request = new Request();
        request.setRequestLine("PUT /form HTTP/1.1");
        request.setBody("Malina");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(StatusCodes.OK, message);
    }

    public void testGetResponseForPOST() {
        Request request = new Request();
        request.setRequestLine("POST /form HTTP/1.1");
        request.setBody("Tran");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(StatusCodes.OK, message);
    }
}