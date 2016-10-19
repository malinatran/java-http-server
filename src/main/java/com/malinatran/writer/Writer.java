package com.malinatran.writer;

import com.malinatran.response.Response;

import java.io.IOException;

public interface Writer {

    void write(Response response) throws IOException;

    void close() throws IOException;
}
