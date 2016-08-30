package com.malinatran;

import java.lang.Runnable;
import java.io.*;

public class ClientHandler implements Runnable {

    private final BufferedWriter out;
    private final BufferedReader in;

    ClientHandler (BufferedWriter out, BufferedReader in) throws IOException {
        this.out = out;
        this.in = in;
    }

    public void run() {
        try {
            getRequestAndResponse(out, in);
            closeStreams(out, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRequestAndResponse(BufferedWriter out, BufferedReader in) throws IOException {
        RequestListener requestListener = new RequestListener();
        Request request = requestListener.getNextRequest(in);
        String response = new Response().getResponse(request);
        out.write(response);
    }

    private void closeStreams(BufferedWriter out, BufferedReader in) throws IOException {
        out.close();
        in.close();
    }
}
