package com.malinatran.writer;

import com.malinatran.mocks.MockResponseWriter;
import com.malinatran.utility.ResponseLogger;
import com.malinatran.response.Response;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static com.malinatran.utility.Status.OK;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ResponseWriterTest {

    ResponseLogger responseLogger;
    MockResponseWriter mockResponseWriter;

    @Before
    public void setUp() throws IOException {
        responseLogger = new ResponseLogger();
        mockResponseWriter = new MockResponseWriter(responseLogger);
    }

    @Test
    public void closeClosesWriter() throws Exception {
        mockResponseWriter.close();

        assertTrue(mockResponseWriter.getClosed());
    }

    @Test
    public void writeWritesToStream() throws Exception {
        String bodyAsString = "testing";
        byte[] body = bodyAsString.getBytes();
        Response response = new Response("HTTP/1.1");
        response.setStatus(OK);
        response.setBodyContent(body);

        mockResponseWriter.write(response);

        assertEquals(OK, response.getStatus());
        assertEquals(body, response.getBodyContent());
    }
}