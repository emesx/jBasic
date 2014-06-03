package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.Operator;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.statements.PrintStatement;

import java.util.ArrayList;
import java.util.List;

public class PrintParser implements StatementParser {
    @Override
    public PrintStatement parse(TokenizingIterator iterator) {
        final List<Expression> expressions = new ArrayList<>();

        do {
            Expression expression = new ExpressionParser().parse(iterator);
            expressions.add(expression);
        } while (iterator.next() == Operator.SEPARATOR); // either we consumed , or LF in which case we end anyway

        return new PrintStatement(expressions);
    }
}
