package com.malinatran.router;

import com.malinatran.request.Request;
import com.malinatran.response.Response;

public interface RouterCallback {

    void run(Request request, Response response);
}
