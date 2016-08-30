package com.malinatran;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResponseTest {

    private String responseOK = "HTTP/1.1 200 OK\r\n";
    private String responseNotFound = "HTTP/1.1 404 Not Found\r\n";
    private String headerAllowAllMethods = "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n\r\n";
    private String headerAllowSomeMethods = "Allow: GET,OPTIONS\r\n\r\n";

    @Test
    public void testGetResponseForGET() {
        Request request = new Request();
        request.setRequestLine("GET / HTTP/1.1");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(responseOK, message);
    }

    @Test
    public void testGetResponseForHEAD() {
        Request request = new Request();
        request.setRequestLine("HEAD / HTTP/1.1");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(responseOK, message);
    }

    @Test
    public void testGetResponseForHEADWithURI() {
        Request request = new Request();
        request.setRequestLine("HEAD /foobar HTTP/1.1");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(responseNotFound, message);
    }

    @Test
    public void testGetResponseForPUT() {
        Request request = new Request();
        request.setRequestLine("PUT /form HTTP/1.1");
        request.setBody("Malina");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(responseOK, message);
    }

    @Test
    public void testGetResponseForPOST() {
        Request request = new Request();
        request.setRequestLine("POST /form HTTP/1.1");
        request.setBody("Tran");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(responseOK, message);
    }

    @Test
    public void testGetResponseForOPTIONS2() {
        Request request = new Request();
        request.setRequestLine("OPTIONS /method_options2 HTTP/1.1");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(responseOK + headerAllowSomeMethods, message);
    }

    @Test
    public void testGetResponseForOPTIONS() {
        Request request = new Request();
        request.setRequestLine("OPTIONS /method_options HTTP/1.1");
        Response response = new Response();
        String message = response.getResponse(request);
        assertEquals(responseOK + headerAllowAllMethods, message);
    }
}