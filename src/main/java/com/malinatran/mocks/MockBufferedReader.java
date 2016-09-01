package com.malinatran.mocks;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MockBufferedReader extends BufferedReader {

    private int numReads = 0;
    private String[] headers;

    public MockBufferedReader(String[] headers) {
        super(new InputStreamReader(System.in));
        this.headers = headers;
    }

    @Override
    public String readLine() {
        return numReads == headers.length ? "" : headers[numReads++];
    }
}