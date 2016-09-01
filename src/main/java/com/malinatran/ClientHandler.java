package com.malinatran;

import com.malinatran.request.Request;
import com.malinatran.request.RequestListener;
import com.malinatran.response.Response;
import com.malinatran.router.Router;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientHandler implements Runnable {

    private final BufferedWriter out;
    private final BufferedReader in;
    private final Router router;

    ClientHandler (BufferedWriter out, BufferedReader in, Router router) throws IOException {
        this.out = out;
        this.in = in;
        this.router = router;
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
        Response response = router.getResponse(request);
        out.write(response.toString());
    }

    private void closeStreams(BufferedWriter out, BufferedReader in) throws IOException {
        out.close();
        in.close();
    }
}
