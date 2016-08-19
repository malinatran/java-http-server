package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        try {
            while (true) {
                int portNumber = 5000;
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                String httpResponse200 = "HTTP/1.1 200 OK\r\n\r\n";
                String httpResponse404 = "HTTP/1.1 404 Not Found\r\n\r\n";
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream()));
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String line = in.readLine();

                while (line != null) {
                    String route = line.split(" ")[1];

                    if (route.equals("/")) {
                        out.write(httpResponse200);
                        break;
                    } else {
                        out.write(httpResponse404);
                        break;
                    }
                }
                out.close();
                in.close();
                clientSocket.close();
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
