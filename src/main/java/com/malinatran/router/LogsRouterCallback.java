package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class LogsRouterCallback implements RouterCallback {

    private Boolean validCredentials(String credentials) {
        return ((credentials != null) && credentials.equals("Basic YWRtaW46aHVudGVyMg=="));
    }

    public void run(Request request, Response response) {
        String credentials = request.getHeaderValue("Authorization");

       if (validCredentials(credentials)) {
            response.setStatus(Status.OK);
        } else if (credentials == null || !validCredentials(credentials)) {
            response.setStatus(Status.UNAUTHORIZED);
            response.setHeader("WWW-Authenticate", "Basic realm=MALINA_REALM");
        }
    }
}
