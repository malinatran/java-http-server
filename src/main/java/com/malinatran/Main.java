package com.malinatran;

import com.malinatran.reader.Reader;
import com.malinatran.reader.RequestReader;
import com.malinatran.routing.Action;
import com.malinatran.setup.ClientHandler;
import com.malinatran.setup.CommandLinePrinter;
import com.malinatran.setup.ServerConfiguration;
import com.malinatran.utility.Mapping;
import com.malinatran.utility.RequestLogger;
import com.malinatran.routing.Router;
import com.malinatran.writer.ResponseWriter;
import com.malinatran.writer.Writer;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.malinatran.setup.Arg.BUSY;
import static com.malinatran.setup.PortArg.PORT;

public class Main {

    private static Socket clientSocket;
    private static ExecutorService executor = Executors.newFixedThreadPool(50);
    private static Reader in;
    private static RequestLogger logger;
    private static Writer out;
    private static Router router;
    private static ServerConfiguration config;
    private static ServerSocket serverSocket;
    private static int port;
    private static String directory;

    public static void main(String[] args) throws IOException {
        runServer(Mapping.getRoutes(), args);
    }

    public static void runServer(Map<String, Action> routes, String[] args) throws IOException {
        config = new ServerConfiguration(args);
        port = config.getPort();
        directory = config.getDirectory();

        setupSocket();
        setupLoggerAndRouter(routes);
        CommandLinePrinter.print(port, directory);

        while (true) {
            startClientHandlerThread();
        }
    }

    private static void setupSocket() throws IOException {
        try {
            serverSocket = new ServerSocket(port);
        } catch (BindException e) {
            printAndTerminate();
        }
    }

    private static void printAndTerminate() {
        CommandLinePrinter.print(PORT, String.valueOf(port), BUSY);
        System.exit(-1);
    }

    private static void setupLoggerAndRouter(Map<String, Action> routes) {
        logger = new RequestLogger();
        router = new Router(routes);
    }

    static void startClientHandlerThread() throws IOException {
        clientSocket = serverSocket.accept();
        out = new ResponseWriter(clientSocket);
        in = new RequestReader(clientSocket);
        ClientHandler clientHandler = new ClientHandler(out, in, logger, router, directory);
        executor.execute(clientHandler);
    }

    protected void finalize() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}
