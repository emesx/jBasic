package it;

import emesx.jbasic.backend.SymbolTable;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ExpressionsTest {

    @Test(timeout = 5000)
    public void test() throws Exception {
        SymbolTable symbols = new SymbolTable();
        TestUtils.runProgram("/it/Expressions.bas", symbols);

        IntStream.range(1, 1+11).forEach(i -> assertEquals(i * 1.0, symbols.get("x" + i)));
    }

}
