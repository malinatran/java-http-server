package com.malinatran.mocks;

import com.malinatran.reader.Reader;

import java.io.IOException;

public class MockRequestReader implements Reader {

    private String[] headers;
    private int numReads;
    private boolean closed;
    private String text;

    public MockRequestReader(String[] headers) {
        this.headers = headers;
        this.numReads = 0;
        this.closed = false;
        this.text = "";
    }

    public void close() throws IOException {
        this.closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public void read(char[] body, int offset, int length) throws IOException {
        text = new String(body).substring(offset, length);
    }

    public String getText() {
        return text;
    }

    public String readLine() {
        return numReads == headers.length ? "" : headers[numReads++];
    }
}