package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.intermediate.ir.statements.ReturnStatement;

public class ReturnParser implements StatementParser {
    @Override
    public ReturnStatement parse(TokenizingIterator iterator) {
        return new ReturnStatement();
    }
}
