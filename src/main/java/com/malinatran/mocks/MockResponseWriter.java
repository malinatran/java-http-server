package com.malinatran.mocks;

import com.malinatran.writer.Writer;

import java.io.IOException;

public class MockResponseWriter implements Writer {

    private MessageLogger messageLogger;

    public MockResponseWriter(MessageLogger messageLogger) throws IOException {
        this.messageLogger = messageLogger;
    }

    public void write(String response) {
        messageLogger.logMessage(response);
    }

    public void close() {
    }
}


