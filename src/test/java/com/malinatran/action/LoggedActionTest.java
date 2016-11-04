package com.malinatran.action;

import com.malinatran.constant.Status;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoggedActionTest {

    LoggedAction loggedAction = new LoggedAction();

    @Test
    public void setLogsToBodyReturns200AndGetsAllLoggedRequests() {
        RequestLogger logger = new RequestLogger();
        Response response = new Response("HTTP/1.1");

        loggedAction.setLogs(response, logger);

        assertEquals(Status.OK, response.getStatus());
        assertNotNull(response.getBodyContent());
    }
}