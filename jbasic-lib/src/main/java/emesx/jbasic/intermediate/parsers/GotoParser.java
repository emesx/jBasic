package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.LineFeed;
import emesx.jbasic.frontend.tokens.Number;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.statements.GotoStatement;

public class GotoParser implements StatementParser {
    @Override
    public GotoStatement parse(TokenizingIterator iterator) {
        int jumpLineNumber = ((Number) iterator.next()).getValue().intValue();
        ParserUtils.consume(iterator, new LineFeed());
        return new GotoStatement(jumpLineNumber);
    }
}
