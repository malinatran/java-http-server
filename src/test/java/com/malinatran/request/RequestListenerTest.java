package com.malinatran.request;

import com.malinatran.reader.Reader;
import com.malinatran.mocks.MockRequestReader;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

public class RequestListenerTest {

    @Test
    public void getNextRequestReturnsWithRequestLineHeadersBody() throws IOException {
        Reader reader = new MockRequestReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost:5000", ""});
        RequestListener requestListener = new RequestListener();

        Request request = requestListener.getNextRequest(reader, "/path/to/somewhere/");

        assertEquals("GET", request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("MalinaBrowser", request.getHeaderValue("User-Agent"));
    }
}
