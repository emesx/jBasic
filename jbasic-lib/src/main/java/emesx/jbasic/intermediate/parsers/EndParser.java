package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.intermediate.ir.statements.EndStatement;

public class EndParser implements StatementParser {
    @Override
    public EndStatement parse(TokenizingIterator iterator) {
        return new EndStatement();
    }
}
