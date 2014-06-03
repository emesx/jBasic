package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.Identifier;
import emesx.jbasic.frontend.tokens.LineFeed;
import emesx.jbasic.frontend.tokens.Operator;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.statements.DefStatement;

import java.util.ArrayList;
import java.util.List;

import static emesx.jbasic.utils.Utils.fmt;

public class DefParser implements StatementParser {
    @Override
    public DefStatement parse(TokenizingIterator iterator) {
        final String name = ((Identifier) iterator.next()).getValue();
        final List<String> parameters = new ArrayList<>();

        ParserUtils.consume(iterator, Operator.LEFT_PARENTHESIS);

        if (iterator.peek() instanceof Identifier) {
            parseParameter(iterator, parameters);
        }

        while (iterator.peek() != Operator.RIGHT_PARENTHESIS) {
            ParserUtils.consume(iterator, Operator.SEPARATOR);
            parseParameter(iterator, parameters);
        }

        if (parameters.size() != parameters.stream().map(String::toLowerCase).distinct().count())
            throw new RuntimeException(fmt("Duplicate parameter name in function `%s` definition", name));

        ParserUtils.consume(iterator, Operator.RIGHT_PARENTHESIS);
        ParserUtils.consume(iterator, Operator.EQUAL_TO);

        Expression expression = new ExpressionParser().parse(iterator);

        ParserUtils.consume(iterator, new LineFeed());

        return new DefStatement(name, parameters, expression);
    }

    private void parseParameter(TokenizingIterator iterator, List<String> parameters) {
        Identifier parameter = ((Identifier) iterator.next());
        parameters.add(parameter.getValue());
    }
}
