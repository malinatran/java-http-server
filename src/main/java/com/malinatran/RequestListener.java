package com.malinatran;

import java.io.BufferedReader;
import java.io.IOException;

@SuppressWarnings("ALL")
public class RequestListener {
    public Request getNextRequest(BufferedReader in) {
        try {
            Request request = new Request();
            request.setRequestLine(in.readLine());

            String line;
            while (((line = in.readLine().trim()) != null) && !line.isEmpty()) {
                request.setHeader(line);
            }
            return request;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
