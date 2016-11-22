package com.malinatran;

import com.malinatran.reader.Reader;
import com.malinatran.reader.RequestReader;
import com.malinatran.setup.ClientHandler;
import com.malinatran.setup.ErrorMessagePrinter;
import com.malinatran.setup.OptionalArgs;
import com.malinatran.setup.ServerSettings;
import com.malinatran.utility.RequestLogger;
import com.malinatran.routing.Router;
import com.malinatran.writer.ResponseWriter;
import com.malinatran.writer.Writer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static Socket clientSocket;
    private static ExecutorService executor = Executors.newFixedThreadPool(50);
    private static Reader in;
    private static RequestLogger logger;
    private static Writer out;
    private static Router router;
    private static ServerSettings settings;
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        settings = OptionalArgs.configureServer(args);
        setupSocket();
        setupLoggerAndRouter();
        OptionalArgs.printArgs();

        while (true) {
            startClientHandlerThread();
        }
    }

    private static void setupSocket() {
        try {
            serverSocket = new ServerSocket(settings.getPort());
        } catch (Exception e) {
            ErrorMessagePrinter.print(ErrorMessagePrinter.PORT, settings.getPort(), ErrorMessagePrinter.BUSY);
            System.exit(0);
        }
    }

    private static void setupLoggerAndRouter() {
        logger = new RequestLogger();
        router = new Router();
        router.setupRoutes();
    }

    private static void startClientHandlerThread() throws IOException {
        clientSocket = serverSocket.accept();
        out = new ResponseWriter(clientSocket);
        in = new RequestReader(clientSocket);
        ClientHandler clientHandler = new ClientHandler(out, in, logger, router, settings.getDirectory());
        executor.execute(clientHandler);
    }

    protected void finalize() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}