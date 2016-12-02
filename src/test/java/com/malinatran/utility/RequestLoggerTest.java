package com.malinatran.utility;

import com.malinatran.request.Request;
import com.malinatran.response.Formatter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestLoggerTest {

    private RequestLogger logger;
    private Request request;
    private Request secondRequest;

    @Before
    public void setUp() {
        logger = new RequestLogger();
        request = new Request();
        secondRequest = new Request();
    }

    @Test
    public void constructorSetsBody() {
        char[] emptyCharArray = new char[0];

        logger = new RequestLogger();

        assertEquals(emptyCharArray.getClass(), logger.getBody().getClass());
    }

    @Test
    public void hasBodyReturnsTrue() {
        char[] body = "Supergirl".toCharArray();
        request.setRequestLine("PUT /form HTTP/1.1");
        request.setBody(body);
        logger.logRequest(request);

        boolean result = logger.hasBody();

        assertTrue(result);
    }

    @Test
    public void hasBodyReturnsFalse() {
        request.setRequestLine("PUT /lala HTTP/1.1");
        logger.logRequest(request);

        boolean result = logger.hasBody();

        assertFalse(result);
    }

    @Test
    public void getLoggedRequestsReturnsAllRequestLines() {
        String expected = "PUT /these HTTP/1.1";
        request.setRequestLine(expected);
        logger.logRequest(request);

        String actual = logger.getLoggedRequests();

        assertEquals(Formatter.addCRLF(expected), actual);
    }

    @Test
    public void logRequestSetsETagAndBody() {
        String eTag = "The Flash";
        char[] body = "Flashpoint".toCharArray();
        request.setRequestLine("PATCH /what HTTP/1.1");
        request.setHeader("If-Match: " + eTag);
        request.setBody(body);

        logger.logRequest(request);

        assertEquals(eTag, logger.getETag());
        assertEquals(new String(body), new String(logger.getBody()));
    }

    @Test
    public void logRequestSetsBodyOnly() {
        char[] body = "The Green Arrow".toCharArray();
        request.setRequestLine("PUT /form HTTP/1.1");
        request.setBody(body);

        logger.logRequest(request);

        assertEquals(new String(body), new String(logger.getBody()));
    }

    @Test
    public void logRequestSetsEmptyBodyIfNotSpecialRequest() {
        char[] body = "I will be empty".toCharArray();
        request.setRequestLine("PUT /somewhere HTTP/1.1");
        request.setBody(body);

        logger.logRequest(request);

        assertEquals("", new String(logger.getBody()));
    }

    @Test
    public void logRequestStoresStringToLogger() {
        request.setRequestLine("GET / HTTP/1.1");

        logger.logRequest(request);

        assertEquals("GET / HTTP/1.1" + Formatter.CRLF, logger.getLoggedRequests());
    }

    @Test
    public void logRequestStoresMultipleStringsToLogger() {
        request.setRequestLine("PUT /these HTTP/1.1");
        secondRequest.setRequestLine("GET /logs HTTP/1.1");

        logger.logRequest(request);
        logger.logRequest(secondRequest);

        assertEquals("PUT /these HTTP/1.1" + Formatter.CRLF + "GET /logs HTTP/1.1" + Formatter.CRLF, logger.getLoggedRequests());
    }
}
