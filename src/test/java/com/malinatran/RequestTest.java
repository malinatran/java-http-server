package com.malinatran;

import org.junit.Test;
import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void testSetHeader() {
        Request request = new Request();
        request.setHeader("Host: google.com");
        assertEquals("google.com", request.getHeaderValue("Host"));
        assertTrue(request.hasHeader("Host"));
    }

    @Test
    public void testSetRequestLine() {
        Request request = new Request();
        request.setRequestLine("GET /path/to/file HTTP/1.0");
        assertEquals(Method.GET, request.getMethod());
        assertEquals("/path/to/file", request.getPath());
        assertEquals("HTTP/1.0", request.getProtocolAndVersion());
    }

    @Test
    public void testSetBody() {
        Request request = new Request();
        request.setBody("my=data");
        assertEquals("my=data", request.getBody());
    }
}