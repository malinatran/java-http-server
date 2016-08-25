package com.malinatran;

import static org.junit.Assert.*;

public class RequestTest {
    @org.junit.Test

    public void testSetHeader() {
        Request request = new Request();
        request.setHeader("Host: google.com");
        assertEquals("google.com", request.getHeaderValue("Host"));
        assertTrue(request.hasHeader("Host"));
    }

    public void testSetRequestLine() {
        Request request = new Request();
        request.setRequestLine("GET /path/to/file HTTP/1.0");
        assertEquals("GET", request.getMethodType());
        assertEquals("/path/to/file", request.getUri());
        assertEquals("HTTP/1.0", request.getVersion());
    }
}