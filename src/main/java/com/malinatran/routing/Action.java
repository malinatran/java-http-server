package com.malinatran.routing;

import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;

public interface Action {

    void run(Request request, Response response) throws IOException;
}