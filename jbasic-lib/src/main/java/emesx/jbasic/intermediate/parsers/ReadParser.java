package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.Identifier;
import emesx.jbasic.frontend.tokens.LineFeed;
import emesx.jbasic.frontend.tokens.Operator;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.statements.ReadStatement;

import java.util.ArrayList;
import java.util.List;

public class ReadParser implements StatementParser {
    @Override
    public ReadStatement parse(TokenizingIterator iterator) {
        final List<String> variables = new ArrayList<>();

        Identifier variable = (Identifier) iterator.next();
        variables.add(variable.getValue());

        while (iterator.peek() == Operator.SEPARATOR) {
            iterator.next();
            variable = (Identifier) iterator.next();
            variables.add(variable.getValue());
        }

        ParserUtils.consume(iterator, new LineFeed());

        return new ReadStatement(variables);
    }
}
