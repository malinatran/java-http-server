package com.malinatran;

public class MessageLogger {
    public String response = "";

    public void logMessage(String response) {
        this.response = response;
    }

    public String getLoggedMessage() {
        return response;
    }
}
