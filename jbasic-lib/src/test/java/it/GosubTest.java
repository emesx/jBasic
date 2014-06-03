package it;

import emesx.jbasic.backend.SymbolTable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GosubTest {

    @Test(timeout = 5000)
    public void test() throws Exception {
        SymbolTable symbols = new SymbolTable();
        TestUtils.runProgram("/it/Gosub.bas", symbols);

        assertEquals(10.0, symbols.get("x"));
        assertEquals(-10.0, symbols.get("y"));
        assertEquals(6.0, symbols.get("i"));

    }

}
