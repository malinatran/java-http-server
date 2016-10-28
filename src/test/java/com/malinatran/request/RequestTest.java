package com.malinatran.request;

import com.malinatran.constants.Method;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void setHeaderStoresValuesIntoHashMap() {
        Request request = new Request();

        request.setHeader("Host: google.com");

        assertEquals("google.com", request.getHeaderValue("Host"));
        assertTrue(request.hasHeader("Host"));
    }

    @Test
    public void setRequestLineSetsMethodPathAndProtocolAsStrings() {
        Request request = new Request();

        request.setRequestLine("GET /path/to/file HTTP/1.1");

        assertEquals(Method.GET, request.getMethod());
        assertEquals("/path/to/file", request.getPath());
        assertEquals("HTTP/1.1", request.getProtocolAndVersion());
    }

    @Test
    public void setBodySetsValueAsString() {
        Request request = new Request();

        request.setBody("my=data");

        assertEquals("my=data", request.getBody());
    }

    @Test
    public void getHeaderValueReturnsNullIfHeaderDoesNotExist() {
        Request request = new Request();

        String result = request.getHeaderValue("Range");

        assertNull(result);
    }

    @Test
    public void getRangeValuesWithStartAndEndRangeReturnsHashMapWithBothValues() {
        Request request = new Request();
        request.setHeader("Range: bytes=0-99");
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("Start", 0);
        expected.put("End", 99);

        Map<String, Integer> actual = request.getRangeValues();

        assertEquals(expected.get("Start"), actual.get("Start"));
        assertEquals(expected.get("End"), actual.get("End"));
    }

    @Test
    public void getRangeValuesWithStartRangeAndNoEndRangeReturnsHashMapWithStartValue() {
        Request request = new Request();
        request.setHeader("Range: bytes=4-");
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("Start", 4);

        Map<String, Integer> actual = request.getRangeValues();

        assertEquals(expected.get("Start"), actual.get("Start"));
        assertNull(actual.get("End"));
    }

    @Test
    public void getRangeValuesWithEndRangeAndNoStartRangeReturnsHashMapWithEndValue() {
        Request request = new Request();
        request.setHeader("Range: bytes=-10");
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("End", 10);

        Map<String, Integer> actual = request.getRangeValues();

        assertEquals(expected.get("End"), actual.get("End"));
        assertNull(actual.get("Start"));
    }
}