package com.malinatran.request;

import com.malinatran.mocks.MockBufferedReader;

import java.io.BufferedReader;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestListenerTest {

    @Test
    public void testGetNextRequest() {
        BufferedReader br = new MockBufferedReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost/5000", ""});
        RequestListener requestListener = new RequestListener();
        Request request = requestListener.getNextRequest(br);
        assertEquals("GET", request.getMethod());
        assertEquals("/", request.getPath());
    }
}
