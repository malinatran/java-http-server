package com.malinatran.request;

import com.malinatran.resource.ContentRangeHelper;
import com.malinatran.routing.Header;

import org.junit.Before;
import org.junit.Test;
import java.util.Hashtable;
import java.util.Map;
import static org.junit.Assert.*;

public class RequestTest {

    private Map<String, Integer> expected;
    private Request request;

    @Before
    public void setUp() {
        request = new Request();
        expected = new Hashtable<String, Integer>();
    }

    @Test
    public void setHeaderStoresValuesIntoHashtable() {
        request.setHeader("Host: google.com");

        assertEquals("google.com", request.getHeaderValue(Header.HOST));
        assertTrue(request.hasHeader(Header.HOST));
    }

    @Test
    public void setRequestLineSetsMethodPathAndProtocolAsStrings() {
        request.setRequestLine("GET /path/to/file HTTP/1.1");

        assertEquals(Method.GET, request.getMethod());
        assertEquals("/path/to/file", request.getPath());
        assertEquals("HTTP/1.1", request.getProtocolAndVersion());
    }

    @Test
    public void setBodySetsValueAsCharArray() {
        char[] data = new char[5];
        request.setBody(data);

        assertEquals(data, request.getBody());
    }

    @Test
    public void getHeaderValueReturnsNullIfHeaderDoesNotExist() {
        String result = request.getHeaderValue(Header.RANGE);

        assertNull(result);
    }

    @Test
    public void getRangeValuesWithStartAndEndRangeReturnsHashtableWithBothValues() {
        request.setHeader("Range: bytes=0-99");

        Map<String, Integer> actual = request.getRangeValues();
        int start = actual.get(ContentRangeHelper.START);
        int end = actual.get(ContentRangeHelper.END);

        assertEquals(0, start);
        assertEquals(99, end);
    }

    @Test
    public void getRangeValuesWithStartRangeAndNoEndRangeReturnsHashtableWithStartValue() {
        request.setHeader("Range: bytes=4-");

        Map<String, Integer> actual = request.getRangeValues();
        int start = actual.get(ContentRangeHelper.START);

        assertEquals(4, start);
        assertNull(actual.get(ContentRangeHelper.END));
    }

    @Test
    public void getRangeValuesWithEndRangeAndNoStartRangeReturnsHashtableWithEndValue() {
        request.setHeader("Range: bytes=-10");
        expected.put(ContentRangeHelper.END, 10);

        Map<String, Integer> actual = request.getRangeValues();
        int end = actual.get(ContentRangeHelper.END);

        assertEquals(10, end);
        assertNull(actual.get(ContentRangeHelper.START));
    }
}