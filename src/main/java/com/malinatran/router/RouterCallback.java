package com.malinatran.router;

import com.malinatran.request.Request;
import com.malinatran.response.Response;

public abstract class RouterCallback {
    public abstract void run(Request request, Response response);
}
