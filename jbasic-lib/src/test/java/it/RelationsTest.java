package it;

import emesx.jbasic.backend.SymbolTable;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class RelationsTest {

    @Test(timeout = 5000)
    public void test() throws Exception {
        SymbolTable symbols = new SymbolTable();
        TestUtils.runProgram("/it/Relations.bas", symbols);

        for (String var : asList("a", "b", "c", "d", "e")) {
            assertEquals(true, symbols.get(var + "1"));
            assertEquals(false, symbols.get(var + "2"));
        }

    }

}
