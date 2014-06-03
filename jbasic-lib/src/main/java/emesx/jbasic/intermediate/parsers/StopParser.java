package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.intermediate.ir.statements.StopStatement;

public class StopParser implements StatementParser {
    @Override
    public StopStatement parse(TokenizingIterator iterator) {
        return new StopStatement();
    }
}
