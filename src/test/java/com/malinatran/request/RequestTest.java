package com.malinatran.request;

import com.malinatran.utility.Header;
import com.malinatran.utility.Method;
import static com.malinatran.resource.TextFile.START;
import static com.malinatran.resource.TextFile.END;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class RequestTest {

    private Request request;
    private Map<String, Integer> expected;

    @Before
    public void setUp() {
        request = new Request();
        expected = new HashMap<String, Integer>();
    }

    @Test
    public void setHeaderStoresValuesIntoHashMap() {
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
    public void getRangeValuesWithStartAndEndRangeReturnsHashMapWithBothValues() {
        request.setHeader("Range: bytes=0-99");
        expected.put(START, 0);
        expected.put(END, 99);

        Map<String, Integer> actual = request.getRangeValues();

        assertEquals(expected.get(START), actual.get(START));
        assertEquals(expected.get(END), actual.get(END));
    }

    @Test
    public void getRangeValuesWithStartRangeAndNoEndRangeReturnsHashMapWithStartValue() {
        request.setHeader("Range: bytes=4-");
        expected.put(START, 4);

        Map<String, Integer> actual = request.getRangeValues();

        assertEquals(expected.get(START), actual.get(START));
        assertNull(actual.get(END));
    }

    @Test
    public void getRangeValuesWithEndRangeAndNoStartRangeReturnsHashMapWithEndValue() {
        request.setHeader("Range: bytes=-10");
        expected.put(END, 10);

        Map<String, Integer> actual = request.getRangeValues();

        assertEquals(expected.get(END), actual.get(END));
        assertNull(actual.get(START));
    }
}