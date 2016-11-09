package com.malinatran.response;

import com.malinatran.utility.Header;
import com.malinatran.utility.Status;
import org.junit.Before;
import org.junit.Test;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.*;

public class ResponseBuilderTest {

    private Response response;

    @Before
    public void setUp() {
        response = new Response("HTTP/1.1");
    }

    @Test
    public void imageReturnsResponseWith200StatusCode() {
        String imageType = "jpeg";
        byte[] image = new byte[10];

        ResponseBuilder.image(response, imageType, image);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void partialTextReturnsResponseWith206StatusCodeAndContentRelatedHeaders() {
        String text = "partial text";
        Map<String, Integer> range = new Hashtable<String, Integer>();
        range.put("Start", 1);
        range.put("End", 11);
        int total = 14;

        ResponseBuilder.partialText(response, text, range, total);

        assertEquals(Status.PARTIAL_CONTENT, response.getStatus());
        assertTrue(response.hasHeader(Header.CONTENT_TYPE));
        assertTrue(response.hasHeader(Header.CONTENT_RANGE));
    }

    @Test
    public void textReturnsResponseWith200AndContentType() {
        String text = "text";

        ResponseBuilder.text(response, text);

        assertEquals(Status.OK, response.getStatus());
        assertTrue(response.hasHeader(Header.CONTENT_TYPE));
    }
}