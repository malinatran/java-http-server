package com.malinatran;

import com.malinatran.reader.RequestReader;
import com.malinatran.request.RequestLogger;
import com.malinatran.router.Router;
import com.malinatran.router.Routes;
import com.malinatran.setup.ClientHandler;
import com.malinatran.setup.CommandLineArgsParser;
import com.malinatran.setup.ErrorHandler;
import com.malinatran.setup.ServerSettings;
import com.malinatran.writer.ResponseWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	private static final String PORT = "Port: ";
	private static final String DIRECTORY = "Directory: ";
	private static CommandLineArgsParser parser;
	private static ServerSettings settings;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static RequestLogger requestLogger;
	private static Router router;
	private static int port;
	private static String directory;

	public static void main(String[] args) throws IOException {
		configureServer(args);
		setupSocket();
        setupLoggerAndRouter();
		printArgs(port, directory);

		while (true) {
			startClientHandlerThread();
		}
	}

	private static void configureServer(String[] args) {
		parser = new CommandLineArgsParser(args);
		settings = new ServerSettings(parser.getConfiguration());
		port = settings.getPort();
		directory = settings.getDirectory();
	}

	private static void printArgs(int port, String directory) {
		System.out.println(PORT + String.valueOf(port));
		System.out.println(DIRECTORY + directory);
	}

	private static void setupSocket() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
            ErrorHandler.print(ErrorHandler.PORT, port, ErrorHandler.BUSY);
			System.exit(0);
		}
	}

	private static void setupLoggerAndRouter() {
		requestLogger = new RequestLogger();
        router = new Router();
		new Routes(router);
	}

	private static void startClientHandlerThread() throws IOException {
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
