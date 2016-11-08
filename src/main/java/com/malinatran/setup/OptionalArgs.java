package com.malinatran.setup;

public class OptionalArgs {

	private static final String PORT = "Port: ";
	private static final String DIRECTORY = "Directory: ";
	private static ServerSettings settings;

	public static ServerSettings configureServer(String[] args) {
		CommandLineArgsParser parser = new CommandLineArgsParser(args);
		settings = new ServerSettings(parser.getConfiguration());

		return settings;
	}

	public static void printArgs() {
        int port = settings.getPort();
		String directory = settings.getDirectory();

		System.out.println(PORT + String.valueOf(port));
		System.out.println(DIRECTORY + directory);
	}
}