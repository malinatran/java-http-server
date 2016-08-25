package com.malinatran;

import static org.junit.Assert.*;

public class ResponseTest {
    @org.junit.Test

    public void testGetResponse() {
        Response response = new Response();
        String message = response.getResponse("/");
        assertEquals("HTTP/1.1 200 OK\r\n\r\n", message);
    }
}