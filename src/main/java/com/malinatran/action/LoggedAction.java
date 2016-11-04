package com.malinatran.action;

import com.malinatran.constant.Status;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;

public class LoggedAction {

    public void setLogs(Response response, RequestLogger requestLogger) {
        response.setStatus(Status.OK);
        response.setBodyContent(requestLogger.getLoggedRequests());
    }
}
