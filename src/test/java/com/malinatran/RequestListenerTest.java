package com.malinatran;

import java.io.BufferedReader;

import static org.junit.Assert.*;

public class RequestListenerTest {
    @org.junit.Test

    public void testGetNextRequestFor200() {
        BufferedReader br = new MockBufferedReader("GET / HTTP/1.1");
        RequestListener requestListener = new RequestListener();
        Response response = new Response();
        String responseText = response.getResponse(requestListener.getNextRequest(br).getUri());
        assertEquals(Response.STATUS_OK, responseText);
    }

    public void testGetNextRequestFor404() {
        BufferedReader br = new MockBufferedReader("HEAD /foobar HTTP/1.0");
        RequestListener requestListener = new RequestListener();
        Response response = new Response();
        String responseText = response.getResponse(requestListener.getNextRequest(br).getUri());
        assertEquals(Response.STATUS_NOT_FOUND, responseText);
    }
}
