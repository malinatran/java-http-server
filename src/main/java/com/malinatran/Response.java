package com.malinatran;

public class Response {

    public static final String STATUS_OK = "HTTP/1.1 200 OK\r\n\r\n";
    public static final String STATUS_NOT_FOUND = "HTTP/1.1 404 Not Found\r\n\r\n";

    public String getResponse(String uri) {
        if (uri.equals("/")) {
            return STATUS_OK;
        } else {
            return STATUS_NOT_FOUND;
        }
    }
}