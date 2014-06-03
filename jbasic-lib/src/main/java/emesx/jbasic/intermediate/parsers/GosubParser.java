package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.LineFeed;
import emesx.jbasic.frontend.tokens.Number;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.statements.GosubStatement;

public class GosubParser implements StatementParser {
    @Override
    public GosubStatement parse(TokenizingIterator iterator) {
        int subLineNumber = ((Number) iterator.next()).getValue().intValue();
        ParserUtils.consume(iterator, new LineFeed());
        return new GosubStatement(subLineNumber);
    }
}
