package com.malinatran.utility;

import com.malinatran.routing.Action;
import com.malinatran.routing.IndexAction;
import com.malinatran.routing.OptionsAction;
import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;

public class MappingTest {

    private Map<String, String> encodedCharacters = Mapping.getEncodedCharacters();
    private Map<String, Action> routes = Mapping.getRoutes();

    @Test
    public void getEncodedCharactersReturnsMapWithPercentSymbolsAsKeys() {
        assertTrue(encodedCharacters.containsKey("%3A"));
        assertTrue(encodedCharacters.containsKey("%5B"));
        assertEquals(":", encodedCharacters.get("%3A"));
        assertEquals("[", encodedCharacters.get("%5B"));
    }

    @Test
    public void getEncodedCharactersReturnsMapWithCharactersAsValues() {
        assertTrue(encodedCharacters.containsValue("@"));
        assertTrue(encodedCharacters.containsValue("#"));
    }

    @Test
    public void getEncodedCharactersReturnsNullIfKeyDoesNotExist() {
        assertNull(encodedCharacters.get("%00"));
    }

    @Test
    public void getRoutesReturnsMapWithMethodAndPathAsKeys() {
        assertTrue(routes.containsKey("GET /"));
        assertTrue(routes.containsKey("POST /method_options"));
        assertEquals(new IndexAction().getClass(), routes.get("GET /").getClass());
        assertEquals(new OptionsAction().getClass(), routes.get("POST /method_options").getClass());
    }

    @Test
    public void getRoutesReturnsNullIfKeyDoesNotExist() {
        assertNull(routes.get("BOGUS /request"));
    }
}