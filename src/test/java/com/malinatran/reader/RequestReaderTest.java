package com.malinatran.reader;

import com.malinatran.mocks.MockRequestReader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RequestReaderTest {

    private MockRequestReader mockRequestReader;

    @Before
    public void setUp() throws IOException {
        String[] headers = new String[] { "test" };
        mockRequestReader = new MockRequestReader(headers);
    }

    @Test
    public void closeClosesReader() throws Exception {
        mockRequestReader.close();

        assertTrue(mockRequestReader.isClosed());
    }

    @Test
    public void readReadsFullLineFromStream() throws Exception {
        String helloWorld = "Hello world";
        char[] text = helloWorld.toCharArray();

        mockRequestReader.read(text, 0, 11);

        assertEquals(helloWorld, mockRequestReader.getText());
    }

    @Test
    public void readReadsPartialLineFromStream() throws Exception {
        String helloWorld = "Hello world";
        char[] text = helloWorld.toCharArray();

        mockRequestReader.read(text, 3, 11);

        assertEquals("lo world", mockRequestReader.getText());
    }

    @Test
    public void readLineReadsLineFromStream() throws Exception {
        String result = mockRequestReader.readLine();

        assertEquals("test", result);
    }
}