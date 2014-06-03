package it;

import emesx.jbasic.backend.SymbolTable;
import org.junit.Test;

import java.util.function.Supplier;

import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ForLoopsTest {

    @Test(timeout = 5000)
    public void test() throws Exception {
        SymbolTable symbols = new SymbolTable();
        TestUtils.runProgram("/it/ForLoops.bas", symbols);

        assertEquals((double) range(1, 10 + 1).sum(), symbols.get("x1"));
        assertThrows(() -> symbols.get("i1"));          // index i1 was not present outside, so was nested in loop scope

        assertEquals(2750.0, symbols.get("x2"));
        assertThrows(() -> symbols.get("x2nested"));    // also in loop scope

        assertEquals(11234.0, symbols.get("x3"));       // because of gosubs

        assertEquals(45.0, symbols.get("x4"));          // because it jumped out of loop

        assertEquals(3450.0, symbols.get("x5"));        // it's 3000 + math on non-removed i,j from previous loop

    }

    private void assertThrows(Supplier<?> supplier) {
        try {
            supplier.get();
            fail("Expected to throw");
        } catch (Exception e) {

        }
    }

}
