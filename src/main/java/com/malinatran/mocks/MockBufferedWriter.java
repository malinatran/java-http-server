package com.malinatran.mocks;

import com.malinatran.MessageLogger;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class MockBufferedWriter extends BufferedWriter {

    private MessageLogger messageLogger;

    public MockBufferedWriter(MessageLogger messageLogger) {
        super(new OutputStreamWriter(System.out));
        this.messageLogger = messageLogger;
    }

    public void write(String response) {
        messageLogger.logMessage(response);
    }

    public void close() {
    }
}


