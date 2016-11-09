package com.malinatran.mocks;

import com.malinatran.response.Response;
import com.malinatran.writer.Writer;

import java.io.IOException;

public class MockResponseWriter implements Writer {

    private ResponseLogger responseLogger;

    public MockResponseWriter(ResponseLogger responseLogger) throws IOException {
        this.responseLogger = responseLogger;
    }

    public void close() {}

    public void write(Response response) {
        responseLogger.logResponse(response);
    }
}