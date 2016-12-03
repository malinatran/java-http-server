package com.malinatran.mocks;

import com.malinatran.response.Response;
import com.malinatran.utility.ResponseLogger;
import com.malinatran.writer.Writer;

import java.io.IOException;

public class MockResponseWriter implements Writer {

    private ResponseLogger responseLogger;
    private boolean closed;

    public MockResponseWriter(ResponseLogger responseLogger) throws IOException {
        this.responseLogger = responseLogger;
        this.closed = false;
    }

    public void close() {
        this.closed = true;
    }

    public boolean getClosed() {
        return closed;
    }

    public void write(Response response) {
        responseLogger.logResponse(response);
    }
}