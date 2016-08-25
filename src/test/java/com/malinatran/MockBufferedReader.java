package com.malinatran;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class MockBufferedReader extends BufferedReader {
    private int numReads = 0;
    private String head;

    public MockBufferedReader(String head) {
        super(new InputStreamReader(System.in));
        this.head = head;
    }

    @Override
    public String readLine() {
        numReads++;

        switch (numReads) {
            case 1:
                return head;
            case 2:
                return "User-Agent: MalinaBrowser";
            case 3:
                return "Host: google.com";
            default:
                return "";
        }
    }
}
