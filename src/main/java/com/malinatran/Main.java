package com.malinatran;

import com.malinatran.router.Router;
import com.malinatran.router.Routes;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int PORT_NUMBER = 6000;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ClientHandler clientHandler;
    private static Thread thread;
    private static Router router;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT_NUMBER);
        router = new Router();
        new Routes(router);

        while (true) {
            clientSocket = serverSocket.accept();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientHandler = new ClientHandler(out, in, router);
            thread = new Thread(clientHandler);
            thread.start();
        }
    }

    protected void finalize() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}