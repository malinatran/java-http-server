package com.malinatran;

import com.malinatran.reader.RequestReader;
import com.malinatran.request.RequestLogger;
import com.malinatran.setup.ClientHandler;
import com.malinatran.setup.CommandLineArgsParser;
import com.malinatran.setup.ServerSettings;
import com.malinatran.writer.ResponseWriter;
import com.malinatran.router.Router;
import com.malinatran.router.Routes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static CommandLineArgsParser parser;
    private static ServerSettings settings;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ClientHandler clientHandler;
    private static Thread thread;
    private static Router router;
    private static RequestLogger requestLogger;

    public static void main(String[] args) throws IOException {
        parser = new CommandLineArgsParser(args);
        settings = new ServerSettings(parser.getConfiguration());
        serverSocket = new ServerSocket(settings.getPort());
        router = new Router();
        requestLogger = new RequestLogger();
        new Routes(router);

        while (true) {
            clientSocket = serverSocket.accept();
            ResponseWriter out = new ResponseWriter(clientSocket);
            RequestReader in = new RequestReader(clientSocket);
            clientHandler = new ClientHandler(out, in, requestLogger, router, settings.getPath());
            thread = new Thread(clientHandler);
            thread.start();
        }
    }

    protected void finalize() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}