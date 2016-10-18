package com.malinatran.router;

import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;

public interface RouterCallback {

    void run(Request request, Response response) throws IOException;
}
