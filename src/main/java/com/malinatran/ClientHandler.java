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

            getRequestAndResponse(in, out);
            closeStreamsAndSockets(in, out, clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getRequestAndResponse(BufferedReader in, BufferedWriter out) throws IOException {
        RequestListener requestListener = new RequestListener();
        Request request = requestListener.getNextRequest(in);
        String response = new Response().getResponse(request);
        out.write(response);
    }

    public void closeStreamsAndSockets(BufferedReader in, BufferedWriter out, Socket clientSocket) throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }
}
