package com.malinatran.routing;

import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.utility.RequestLogger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface Action {

    void run(Request request, Response response) throws IOException;

    void run(Response response, RequestLogger logger);

    void run(Request request, Response response, RequestLogger logger) throws IOException, NoSuchAlgorithmException;
}