package com.malinatran;

import java.lang.Runnable;
import java.net.Socket;
import java.io.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    ClientHandler (Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            RequestListener requestListener = new RequestListener();

            Request request = requestListener.getNextRequest(in);
            String uri = request.getUri();
            String response = new Response().getResponse(uri);
            out.write(response);

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
