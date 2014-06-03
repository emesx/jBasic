package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.Keyword;
import emesx.jbasic.frontend.tokens.Number;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.expressions.*;
import emesx.jbasic.intermediate.ir.statements.IfStatement;

public class IfParser implements StatementParser {
    @Override
    public IfStatement parse(TokenizingIterator iterator) {
        Expression condition = new ExpressionParser().parse(iterator);

        if (!ParserUtils.instanceOfAny(
                condition,
                EqualityExpression.class, NotEqualityExpression.class,
                GreaterThanExpression.class, GreaterThanEqualExpression.class,
                LessThanExpression.class, LessThanEqualExpression.class)) {

            throw new RuntimeException("IF statement requires a boolean condition");
        }

        ParserUtils.consume(iterator, Keyword.THEN);

        Number jumpLineNumber = (Number) iterator.next();

        return new IfStatement(condition, jumpLineNumber.getValue().intValue());
    }
}
