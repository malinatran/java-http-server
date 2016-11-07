package com.malinatran.router;

import com.malinatran.request.Request;
import com.malinatran.request.RequestLogger;
import com.malinatran.response.Response;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface RouterCallback {

    void run(Request request, Response response) throws IOException;
    void run(Response response, RequestLogger logger) throws IOException;
    void run(Request request, Response response, RequestLogger logger) throws IOException, NoSuchAlgorithmException;
}
