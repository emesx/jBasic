package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.Identifier;
import emesx.jbasic.frontend.tokens.LineFeed;
import emesx.jbasic.frontend.tokens.Operator;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.statements.LetStatement;


public class LetParser implements StatementParser {
    @Override
    public LetStatement parse(TokenizingIterator iterator) {
        Identifier variable = (Identifier) iterator.next();

        ParserUtils.consume(iterator, Operator.EQUAL_TO);

        Expression expression = new ExpressionParser().parse(iterator);

        ParserUtils.consume(iterator, new LineFeed());

        return new LetStatement(variable.getValue(), expression);
    }
}
