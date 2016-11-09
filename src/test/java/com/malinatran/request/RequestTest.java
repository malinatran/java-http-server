package com.malinatran.request;

import com.malinatran.utility.Header;
import com.malinatran.utility.Method;

import org.junit.Before;
import org.junit.Test;
import java.util.Hashtable;
import java.util.Map;
import static org.junit.Assert.*;

public class RequestTest {

    private String START = "Start";
    private String END = "End";
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
        expected.put(START, 0);
        expected.put(END, 99);

        Map<String, Integer> actual = request.getRangeValues();

        assertEquals(expected.get(START), actual.get(START));
        assertEquals(expected.get(END), actual.get(END));
    }

    @Test
    public void getRangeValuesWithStartRangeAndNoEndRangeReturnsHashtableWithStartValue() {
        request.setHeader("Range: bytes=4-");
        expected.put(START, 4);

        Map<String, Integer> actual = request.getRangeValues();

        assertEquals(expected.get(START), actual.get(START));
        assertNull(actual.get(END));
    }

    @Test
    public void getRangeValuesWithEndRangeAndNoStartRangeReturnsHashtableWithEndValue() {
        request.setHeader("Range: bytes=-10");
        expected.put(END, 10);

        Map<String, Integer> actual = request.getRangeValues();

        assertEquals(expected.get(END), actual.get(END));
        assertNull(actual.get(START));
    }
}