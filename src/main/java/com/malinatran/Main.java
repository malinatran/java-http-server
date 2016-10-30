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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Router router;
    private static RequestLogger requestLogger;
    private static int port;
    private static String directory;

    public static void main(String[] args) throws IOException {
        configureServer(args);
        setupRouterAndLogger();
        setupServerSocket();

        while (true) {
            startThread();
        }
    }

    private static void configureServer(String[] args) throws IOException {
        CommandLineArgsParser parser = new CommandLineArgsParser(args);
        ServerSettings settings = new ServerSettings(parser.getConfiguration());
        port = settings.getPort();
        directory = settings.getDirectory();
        printArgs(port, directory);
        Path path = Paths.get(directory);

        if (Files.notExists(path)) {
            System.out.println("Directory is not found! Try again?");
            System.exit(0);
        }
    }

    private static void setupServerSocket() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Hrm... looks like this port is busy. Try again?");
            System.exit(0);
        }
    }

    private static void setupRouterAndLogger() {
        router = new Router();
        requestLogger = new RequestLogger();
        new Routes(router);
    }

    private static void printArgs(int port, String directory) {
        System.out.println("Port: " + String.valueOf(port));
        System.out.println("Directory: " + directory + "\n");
    }

    private static void startThread() throws IOException {
        clientSocket = serverSocket.accept();
        ResponseWriter out = new ResponseWriter(clientSocket);
        RequestReader in = new RequestReader(clientSocket);
        ClientHandler clientHandler = new ClientHandler(out, in, requestLogger, router, directory);
        Thread thread = new Thread(clientHandler);
        thread.start();
    }

    protected void finalize() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}