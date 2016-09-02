package com.malinatran;

import com.malinatran.reader.RequestReader;
import com.malinatran.router.Logger;
import com.malinatran.writer.ResponseWriter;
import com.malinatran.router.Router;
import com.malinatran.router.Routes;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int PORT_NUMBER = 5000;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ClientHandler clientHandler;
    private static Thread thread;
    private static Router router;
    private static Logger logger;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT_NUMBER);
        router = new Router();
        logger = new Logger();
        new Routes(router);

        while (true) {
            clientSocket = serverSocket.accept();
            ResponseWriter out = new ResponseWriter(clientSocket);
            RequestReader in = new RequestReader(clientSocket);
            clientHandler = new ClientHandler(out, in, logger, router);
            thread = new Thread(clientHandler);
            thread.start();
        }
    }

    protected void finalize() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}