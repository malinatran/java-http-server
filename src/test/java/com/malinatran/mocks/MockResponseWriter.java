package com.malinatran.mocks;

import com.malinatran.response.Response;
import com.malinatran.writer.Writer;

import java.io.IOException;

public class MockResponseWriter implements Writer {

    private MessageLogger messageLogger;

    public MockResponseWriter(MessageLogger messageLogger) throws IOException {
        this.messageLogger = messageLogger;
    }

    public void write(Response response) {
        messageLogger.logMessage(response.toString());
    }

    public void close() {
    }
}


