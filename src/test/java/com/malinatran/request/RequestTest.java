package com.malinatran.request;

import com.malinatran.constants.Method;

import org.junit.Test;
import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void setHeaderStoresValuesIntoHashMap() {
        Request request = new Request();

        request.setHeader("Host: google.com");

        assertEquals("google.com", request.getHeaderValue("Host"));
        assertTrue(request.hasHeader("Host"));
    }

    @Test
    public void setRequestLineSetsMethodPathAndProtocolAsStrings() {
        Request request = new Request();

        request.setRequestLine("GET /path/to/file HTTP/1.1");

        assertEquals(Method.GET, request.getMethod());
        assertEquals("/path/to/file", request.getPath());
        assertEquals("HTTP/1.1", request.getProtocolAndVersion());
    }

    @Test
    public void setBodySetsValueAsString() {
        Request request = new Request();

        request.setBody("my=data");

        assertEquals("my=data", request.getBody());
    }
}