package com.malinatran.response;

import com.malinatran.constants.Status;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void testSetAndGetStatus() {
        Response response = new Response("GET", "HTTP/1.1", "/", null);

        response.setStatus(Status.OK);

        assertEquals("200 OK", response.getStatus());
    }

    @Test
    public void testSetHeader() {
        Response response = new Response("POST", "HTTP/1.1", "/", "hello world");

        response.setHeader("Host", "google.com");

        assertTrue(response.hasHeader("Host"));
    }

    @Test
    public void testRedirectTo() {
        Response response = new Response("POST", "HTTP/1.1", "/", "hello world");

        response.redirectTo("malinatran.com");

        assertEquals("302 Found", response.getStatus());
        assertTrue(response.hasHeader("Location"));
    }

    @Test
    public void testGetStatusLine() {
        Response response = new Response("POST", "HTTP/1.1", "/", "hello world");
        response.setStatus(Status.OK);

        String statusLine = response.getStatusLine();

        assertEquals("HTTP/1.1 200 OK", statusLine);
    }

    @Test
    public void testToString() {
        Response response = new Response("PUT", "HTTP/1.1", "/", "hello squirrel");
        response.setStatus(Status.OK);

        String formattedResponse = response.toString();

        assertEquals("HTTP/1.1 200 OK\r\n", formattedResponse);
    }
}