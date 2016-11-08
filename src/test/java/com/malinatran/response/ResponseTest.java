package com.malinatran.response;

import com.malinatran.utility.Header;
import com.malinatran.utility.Status;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseTest {

    private Response response;

    @Before
    public void setup() {
        response = new Response("HTTP/1.1");
    }


    @Test
    public void hasHeaderReturnsTrueIfHeaderExists() {
       response.setHeader(Header.CONTENT_TYPE, "text/plain");

        boolean result = response.hasHeader(Header.CONTENT_TYPE);

        assertTrue(result);
    }

    @Test
    public void hasHeaderReturnsFalseIfHeaderDoesNotExist() {
        boolean result = response.hasHeader(Header.CONTENT_LENGTH);

        assertFalse(result);
    }

    @Test
    public void setStatusSetsValueAsString() {
        response.setStatus(Status.OK);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void setHeaderStoresValuesIntoHashMap() {
        response.setHeader("Host", "google.com");

        assertTrue(response.hasHeader("Host"));
    }

    @Test
    public void setBodyContentAcceptsStringAndSetsAsByteArray() {
        String bodyMessage = "This will exist in the body.";

        response.setBodyContent(bodyMessage);

        assertEquals(bodyMessage, new String(response.getBodyContent()));
    }

    @Test
    public void setBodyContentAcceptsByteArrayAndSetsAsByteArray() {
        int size = 50;
        byte[] bodyMessage = new byte[size];

        response.setBodyContent(bodyMessage);

        assertEquals(bodyMessage, response.getBodyContent());
        assertEquals(size, response.getBodyContent().length);
    }


    @Test
    public void getStatusReturnsStatusCode() {
        response.setStatus(Status.OK);
        String expected = "200 OK";

        String result = response.getStatus();

        assertEquals(expected, result);
    }

    @Test
    public void getResponseHeadersAndBodyReturnsByteArray() {
        response.setHeader(Header.CONTENT_TYPE, "image/png");
        response.setBodyContent("Hello world");

        byte[] result = response.getResponseHeadersAndBody();

        assertEquals(new byte[0].getClass(), result.getClass());
    }

    @Test
    public void getBodyContentReturnsEmptyByteArrayIfBodyContentIsNull() {
       byte[] result = response.getBodyContent();

        assertEquals(0, result.length);
    }

    @Test
    public void getBodyContentReturnsBodyContentAsByteArrayIfBodyContentExists() {
        response.setBodyContent(new byte[100]);

        byte[] result = response.getBodyContent();

        assertEquals(100, result.length);
    }

    @Test
    public void getStatusLineReturnsProtocolAnd200() {
        response.setStatus(Status.OK);

        String statusLine = response.getStatusLine();

        assertEquals("HTTP/1.1 200 OK\r\n", statusLine);
    }
}
