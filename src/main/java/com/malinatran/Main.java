package com.malinatran;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int PORT_NUMBER = 5000;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ClientHandler clientHandler;
    private static Thread thread;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT_NUMBER);

        while (true) {
            clientSocket = serverSocket.accept();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientHandler = new ClientHandler(out, in);
            thread = new Thread(clientHandler);
            thread.start();
        }
    }

    protected void finalize() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}