package it;

import emesx.jbasic.backend.SymbolTable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefTest {

    @Test(timeout = 5000)
    public void test() throws Exception {
        SymbolTable symbols = new SymbolTable();
        TestUtils.runProgram("/it/Definitions.bas", symbols);

        assertEquals(123.0, symbols.get("x1"));
        assertEquals(2.0, symbols.get("x2"));
        assertEquals(-45.0, symbols.get("x3"));
        assertEquals(-123.0, symbols.get("x4"));
        assertEquals(123.0, symbols.get("x5"));
        assertEquals(137.0, symbols.get("x6"));
        assertEquals(25.0, symbols.get("x7"));

    }

}
