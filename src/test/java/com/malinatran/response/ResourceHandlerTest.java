package com.malinatran.response;

import com.malinatran.constant.Header;
import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Image;
import com.malinatran.setup.ServerSettings;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.*;

public class ResourceHandlerTest {

    private String DEFAULT_DIRECTORY = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private ResourceHandler resourceHandler;
    private Request request;
    private Response response;
    private Image image;

    @Before
    public void setUp() {
        resourceHandler = new ResourceHandler();
        request = new Request();
        request.setDirectoryPath(DEFAULT_DIRECTORY);
        response = new Response("HTTP/1.1");
        image = new Image();
    }

    @Test
     public void readReturns200AndSetsContentTypeAsHeader() throws IOException {
        String text = "file1 contents";
        request.setRequestLine("GET /text-file.txt HTTP/1.1");

        resourceHandler.read(request, response);

        assertEquals(Status.OK, response.getStatus());
        assertEquals(text, new String(response.getBodyContent()));
        assertTrue(response.hasHeader(Header.CONTENT_TYPE));
    }

    @Test
     public void readReturns206AndSetsContentRangeAsHeader() throws IOException {
        String text = "ile1 ";
        request.setRequestLine("GET /text-file.txt HTTP/1.1");
        request.setHeader("Range: byte=1-5");

        resourceHandler.read(request, response);

        assertEquals(Status.PARTIAL_CONTENT, response.getStatus());
        assertEquals(text, new String(response.getBodyContent()));
        assertTrue(response.hasHeader(Header.CONTENT_RANGE));
    }

    @Test
    public void readReturns200AndSetsContentLengthAndTypeAsHeaders() throws IOException {
        request.setRequestLine("GET /image.jpeg HTTP/1.1");

        resourceHandler.read(request, response);

        assertEquals(Status.OK, response.getStatus());
        assertTrue(response.hasHeader(Header.CONTENT_TYPE));
        assertTrue(response.hasHeader(Header.CONTENT_LENGTH));
    }
}