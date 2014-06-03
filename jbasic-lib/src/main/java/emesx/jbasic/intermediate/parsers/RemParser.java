package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.LineFeed;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.statements.RemStatement;

public class RemParser implements StatementParser {
    @Override
    public RemStatement parse(TokenizingIterator iterator) {
        while (!(iterator.peek() instanceof LineFeed))
            iterator.next();

        ParserUtils.consume(iterator, new LineFeed());
        return new RemStatement();
    }
}
