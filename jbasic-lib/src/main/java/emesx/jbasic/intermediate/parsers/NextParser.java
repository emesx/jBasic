package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.Identifier;
import emesx.jbasic.frontend.tokens.LineFeed;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.statements.NextStatement;

public class NextParser implements StatementParser {
    @Override
    public NextStatement parse(TokenizingIterator iterator) {
        String variable = ((Identifier) iterator.next()).getValue();
        ParserUtils.consume(iterator, new LineFeed());
        return new NextStatement(variable);
    }
}
