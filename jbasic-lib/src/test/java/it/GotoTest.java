package it;

import emesx.jbasic.backend.SymbolTable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GotoTest {

    @Test(timeout = 5000)
    public void test() throws Exception {
        SymbolTable symbols = new SymbolTable();
        TestUtils.runProgram("/it/Goto.bas", symbols);

        assertEquals(630.0, symbols.get("x"));

    }

}
