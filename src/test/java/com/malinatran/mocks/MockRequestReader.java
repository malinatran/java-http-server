package com.malinatran.mocks;

import com.malinatran.reader.Reader;

import java.io.IOException;

public class MockRequestReader implements Reader {

    private String[] headers;
    private int numReads;

    public MockRequestReader(String[] headers) {
        this.headers = headers;
        numReads = 0;
    }

    public String readLine() {
        return numReads == headers.length ? "" : headers[numReads++];
    }

    public void read(char[] body, int offset, int length) throws IOException {

    }

    public void close() throws IOException {

    }
}