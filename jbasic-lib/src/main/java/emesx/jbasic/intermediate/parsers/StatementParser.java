package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.intermediate.ir.Statement;

@FunctionalInterface
public interface StatementParser {
    /**
     * Expects no statement name; Consumes trailing LF
     */
    public Statement parse(TokenizingIterator iterator);
}
