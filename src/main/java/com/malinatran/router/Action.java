package com.malinatran.router;

import com.malinatran.request.Request;
import com.malinatran.utility.RequestLogger;
import com.malinatran.response.Response;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface Action {

    void run(Request request, Response response) throws IOException;
}