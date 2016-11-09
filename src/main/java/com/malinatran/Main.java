package com.malinatran;

import com.malinatran.reader.RequestReader;
import com.malinatran.request.RequestLogger;
import com.malinatran.router.Router;
import com.malinatran.router.Routes;
import com.malinatran.setup.*;
import com.malinatran.writer.ResponseWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	private static RequestLogger logger;
	private static Router router;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
    private static ServerSettings settings;

	public static void main(String[] args) throws IOException {
		settings = OptionalArgs.configureServer(args);
		setupSocket();
        setupLoggerAndRouter();
		OptionalArgs.printArgs();

		while (true) {
			startClientHandlerThread();
		}
	}

	private static ExecutorService executor = Executors.newFixedThreadPool(100);

	private static void setupSocket() {
		try {
			serverSocket = new ServerSocket(settings.getPort());
		} catch (Exception e) {
            ErrorHandler.print(ErrorHandler.PORT, settings.getPort(), ErrorHandler.BUSY);
			System.exit(0);
		}
	}

	private static void setupLoggerAndRouter() {
		logger = new RequestLogger();
        router = new Router();
		new Routes(router);
	}

	private static void startClientHandlerThread() throws IOException {
		clientSocket = serverSocket.accept();
		ResponseWriter out = new ResponseWriter(clientSocket);
		RequestReader in = new RequestReader(clientSocket);
		ClientHandler clientHandler = new ClientHandler(out, in, logger, router, settings.getDirectory());
        executor.execute(clientHandler);
//		Thread thread = new Thread(clientHandler);
//		thread.start();
	}

	protected void finalize() throws IOException {
		clientSocket.close();
		serverSocket.close();
	}
}