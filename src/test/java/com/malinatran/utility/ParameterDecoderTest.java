package com.malinatran.utility;

import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class ParameterDecoderTest {

    private String path;
    private String expected;
    private String actual;

    @Test
    public void decodeTextModifiesDecoderMapping() {
        path = "/parameters?hi%there";

        ParameterDecoder.decodeText(path);

        Map<String, String> map = ParameterDecoder.getEncodedCharacters();
        assertTrue(map.containsKey("%2B"));
        assertEquals("+", map.get("%2B"));
    }

    @Test
    public void decodeTextReturnsDecodedText() {
        path = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
        expected = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?\nvariable_2 = stuff\n";

        actual = ParameterDecoder.decodeText(path);

        assertEquals(expected, actual);
    }

    @Test
    public void decodeTextWithoutParametersQueryDoesNotReturnDecodedText() {
        path = "/hello?is%20anyone%20out%20there%3F";
        expected = "";

        actual = ParameterDecoder.decodeText(path);

        assertEquals(expected, actual);
    }
}