package com.malinatran.writer;

import com.malinatran.response.Response;

import java.io.IOException;

public interface Writer {

    void close() throws IOException;

    void write(Response response) throws IOException;
}