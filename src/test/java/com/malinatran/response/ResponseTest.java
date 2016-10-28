package com.malinatran.response;

import com.malinatran.constants.Status;
import com.malinatran.router.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ResponseTest {

    Response response = new Response("HTTP/1.1");

    @Test
    public void redirectToReturns302WithLocationHeader() {
        response.redirectTo("malinatran.com");

        assertEquals(Status.FOUND, response.getStatus());
        assertTrue(response.hasHeader("Location"));
    }

    @Test
    public void setLogsToBodyReturns200AndGetsAllLoggedRequests() {
        Logger logger = new Logger();

        response.setLogsToBody(logger);

        assertEquals(Status.OK, response.getStatus());
        assertNotNull(response.getBodyContent());
    }

    @Test
     public void setTextReturns200AndSetsContentTypeAsHeader() {
        String text = "Just a text file";

        response.setText(text);

        assertEquals(Status.OK, response.getStatus());
        assertTrue(response.hasHeader("Content-Type"));
    }

    @Test
     public void setPartialTextReturns206AndSetsContentRangeAsHeader() {
        String text = "Just a text file";
        Map<String, Integer> map = new HashMap<String, Integer>();

        response.setPartialText(text, map);

        assertEquals(Status.PARTIAL_CONTENT, response.getStatus());
        assertTrue(response.hasHeader("Content-Range"));
    }

    @Test
    public void setImageReturns200AndSetsContentLengthAndTypeAsHeaders() {
        String fileType = "jpg";
        byte[] image = new byte[20];

        response.setImage(fileType, image);

        assertTrue(response.hasHeader("Content-Type"));
        assertTrue(response.hasHeader("Content-Length"));
    }

    @Test
    public void hasHeaderReturnsTrueIfHeaderExists() {
       response.setHeader("Content-Type", "text/plain");

        Boolean result = response.hasHeader("Content-Type");

        assertTrue(result);
    }

    @Test
    public void hasHeaderReturnsFalseIfHeaderDoesNotExist() {
        Boolean result = response.hasHeader("Content-Length");

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
        response.setHeader("Content-Type", "image/png");
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