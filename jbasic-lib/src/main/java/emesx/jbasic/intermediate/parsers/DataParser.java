package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.LineFeed;
import emesx.jbasic.frontend.tokens.Operator;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.expressions.ConstantExpression;
import emesx.jbasic.intermediate.ir.expressions.NegatingExpression;
import emesx.jbasic.intermediate.ir.statements.DataStatement;

import java.util.ArrayList;
import java.util.List;

public class DataParser implements StatementParser {
    @Override
    public DataStatement parse(TokenizingIterator iterator) {
        final List<Double> numbers = new ArrayList<>();

        addToData(iterator, numbers);

        while (iterator.peek() == Operator.SEPARATOR) {
            ParserUtils.consume(iterator, Operator.SEPARATOR);
            addToData(iterator, numbers);
        }

        ParserUtils.consume(iterator, new LineFeed());

        return new DataStatement(numbers);
    }

    private void addToData(TokenizingIterator iterator, List<Double> numbers) {
        Expression expression = new ExpressionParser().parse(iterator);
        int sign = 1;

        if (expression instanceof NegatingExpression) {
            expression = ((NegatingExpression) expression).getExpression();
            sign = -1;
        }

        if (!(expression instanceof ConstantExpression))
            throw new RuntimeException("DATA can only contain constants");

        numbers.add(sign * (double) ((ConstantExpression) expression).getValue());
    }
}
