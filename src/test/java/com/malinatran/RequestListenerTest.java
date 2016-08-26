package com.malinatran;

import java.io.BufferedReader;
import java.io.IOException;
import static org.junit.Assert.*;

public class RequestListenerTest {
    @org.junit.Test

    public void testSetRequestHeaders() throws IOException {
        BufferedReader br = new MockBufferedReader(new String[]
                {"User-Agent: MrRobot", "Host: fsociety", "Content-Type: application/x-www-form-urlencoded", "Content-Length: 17"});
        Request request = new Request();
        RequestListener rl = new RequestListener();
        rl.setRequestHeaders(request, br);
        assertEquals(true, request.hasHeader("User-Agent"));
        assertEquals(true, request.hasHeader("Host"));
        assertEquals(true, request.hasHeader("Content-Length"));
    }

    public void testSetRequestBody() throws IOException {
        BufferedReader br = new MockBufferedReader(new String[]
                {"PUT /form HTTP/1.1", "Content-Length: 7", "Host: malinatran.com", "", "my=data"});
        Request request = new Request();
        RequestListener rl = new RequestListener();
        rl.setRequestHeaders(request, br);
        rl.setRequestBody(request, br);
        assertEquals("my-data", request.getBody());
    }

    public void testGetNextRequestFor200() {
        BufferedReader br = new MockBufferedReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost/5000", ""});
        RequestListener requestListener = new RequestListener();
        Response response = new Response();
        String responseText = response.getResponse(requestListener.getNextRequest(br));
        assertEquals(StatusCodes.OK, responseText);
    }

    public void testGetNextRequestFor404() {
        BufferedReader br = new MockBufferedReader(new String[]
                {"HEAD /foobar HTTP/1.1", "User-Agent: MalinaBrowser", "Host: google.com", ""});
        RequestListener requestListener = new RequestListener();
        Response response = new Response();
        String responseText = response.getResponse(requestListener.getNextRequest(br));
        assertEquals(StatusCodes.NOT_FOUND, responseText);
    }
}
