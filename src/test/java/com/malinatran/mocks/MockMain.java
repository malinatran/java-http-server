package com.malinatran.mocks;

import com.malinatran.Main;
import com.malinatran.routing.Router;
import com.malinatran.setup.CommandLinePrinter;
import com.malinatran.setup.ServerConfiguration;
import com.malinatran.utility.RequestLogger;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import static com.malinatran.setup.Arg.BUSY;
import static com.malinatran.setup.PortArg.PORT;

public class MockMain extends Main {

    private static RequestLogger logger;
    private static Router router;
    private static ServerConfiguration config;
    private static ServerSocket serverSocket;
    private static String directory;
    private static int port;

    public static void main(String[] args) throws IOException {
        config = new ServerConfiguration(args);
        port = config.getPort();
        directory = config.getDirectory();

        setupSocket();
        setupLoggerAndRouter();
        CommandLinePrinter.print(port, directory);
    }

    public int getPort() {
        return port;
    }

    public String getDirectory() {
        return directory;
    }

    public RequestLogger getLogger() {
        return logger;
    }

    public Router getRouter() {
        return router;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void closeSocket() throws IOException {
        serverSocket.close();
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
    }

    private static void setupLoggerAndRouter() {
        logger = new RequestLogger();
        router = new Router();
    }

    protected void finalize() throws IOException {
        serverSocket.close();
    }
}