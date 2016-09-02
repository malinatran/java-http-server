package com.malinatran.router;

import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class RedirectRouterCallback implements RouterCallback {

    public void run(Request request, Response response) {
        response.redirectTo("http://localhost:5000/");
    }
}
