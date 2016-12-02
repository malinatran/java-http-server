package com.malinatran.utility;

import com.malinatran.request.Request;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class RequestBuilderTest {

    private RequestBuilder builder;
    private Request request;

    @Before
    public void setUp() {
        builder = new RequestBuilder();
        request = new Request();
    }

    @Test
    public void getRequestBodyForPatchReturnsBodyAndETag() {
        String body = "Gatsby the Corgi";
        String eTag = "I Heart Dogs";
        request.setRequestLine("PATCH /that HTTP/1.1");
        request.setHeader("If-Match: " + eTag);
        request.setBody(body.toCharArray());

        Map<String, String> actual = builder.getRequestBody(request);

        assertEquals(body, actual.get("body"));
        assertEquals(eTag, actual.get("eTag"));
    }

    @Test
    public void getRequestBodyForPutReturnsBody() {
        String body = "Dogs of 8LA";
        request.setRequestLine("PUT /form HTTP/1.1");
        request.setBody(body.toCharArray());

        Map<String, String> actual = builder.getRequestBody(request);

        assertEquals(body, actual.get("body"));
    }

    @Test
    public void getRequestBodyForPostReturnsBody() {
        String body = "Inya (Face)";
        request.setRequestLine("POST /form HTTP/1.1");
        request.setBody(body.toCharArray());

        Map<String, String> actual = builder.getRequestBody(request);

        assertEquals(body, actual.get("body"));
    }

    @Test
    public void getRequestForDeleteReturnsEmptyBody() {
        request.setBody("I won't exist".toCharArray());
        request.setRequestLine("DELETE /form HTTP/1.1");

        Map<String, String> actual = builder.getRequestBody(request);

        assertEquals("", actual.get("body"));
    }
}