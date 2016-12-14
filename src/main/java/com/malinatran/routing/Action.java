package com.malinatran.routing;

import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.utility.RequestLogger;

import java.io.IOException;

public abstract class Action {

    public void run(Response response, RequestLogger logger) {};

    public void run(Request request, Response response, RequestLogger logger) throws IOException {};
}
