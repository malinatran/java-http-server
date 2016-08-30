package com.malinatran;

import java.io.BufferedReader;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestListenerTest {

    private String responseOK = "HTTP/1.1 200 OK\r\n";
    private String responseNotFound = "HTTP/1.1 404 Not Found\r\n";

    @Test
    public void testGetNextRequestFor200() {
        BufferedReader br = new MockBufferedReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost/5000", ""});
        RequestListener requestListener = new RequestListener();
        Response response = new Response();
        String responseText = response.getResponse(requestListener.getNextRequest(br));
        assertEquals(responseOK, responseText);
    }

    @Test
    public void testGetNextRequestFor404() {
        BufferedReader br = new MockBufferedReader(new String[]
                {"HEAD /foobar HTTP/1.1", "User-Agent: MalinaBrowser", "Host: google.com", ""});
        RequestListener requestListener = new RequestListener();
        Response response = new Response();
        String responseText = response.getResponse(requestListener.getNextRequest(br));
        assertEquals(responseNotFound, responseText);
    }
}
