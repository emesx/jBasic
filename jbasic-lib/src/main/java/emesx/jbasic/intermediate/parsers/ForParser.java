package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.Identifier;
import emesx.jbasic.frontend.tokens.Keyword;
import emesx.jbasic.frontend.tokens.Operator;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.expressions.ConstantExpression;
import emesx.jbasic.intermediate.ir.Statement;
import emesx.jbasic.intermediate.ir.statements.ForStatement;
import emesx.jbasic.intermediate.ir.statements.NextStatement;

import java.util.ArrayList;
import java.util.List;

public class ForParser implements StatementParser {
    @Override
    public ForStatement parse(TokenizingIterator iterator) {
        String index = ((Identifier) iterator.next()).getValue();

        ParserUtils.consume(iterator, Operator.EQUAL_TO);
        Expression initial = new ExpressionParser().parse(iterator);

        ParserUtils.consume(iterator, Keyword.TO);
        Expression limit = new ExpressionParser().parse(iterator);

        Expression step = parseStepExpression(iterator);

        List<Statement> statements = parseStatements(iterator, index);

        ForStatement forStatement = new ForStatement(index, initial, limit, step, statements);
        ParserUtils.linkStatements(forStatement, statements.get(0));

        return forStatement;
    }

    private Expression parseStepExpression(TokenizingIterator iterator) {
        Expression step = new ConstantExpression(1.0);
        if (iterator.peek() == Keyword.STEP) {
            ParserUtils.consume(iterator, Keyword.STEP);
            step = new ExpressionParser().parse(iterator);
        }
        return step;
    }

    private List<Statement> parseStatements(TokenizingIterator iterator, String index) {
        final List<Statement> statements = new ArrayList<>();

        BasicParser basicParser = new BasicParser(iterator);
        while (basicParser.hasNext() && !(basicParser.peek() instanceof NextStatement)) {
            Statement statement = basicParser.next();
            statements.add(statement);
        }

        if (!basicParser.hasNext())
            throw new RuntimeException("FOR loop has no corresponding NEXT statement");

        NextStatement nextStatement = (NextStatement) basicParser.next();
        statements.add(nextStatement);

        if (!nextStatement.getVariable().equalsIgnoreCase(index))
            throw new RuntimeException("FOR loop has index variable not matching the one in NEXT statement");


        ParserUtils.sortLinkStatements(statements);
        return statements;
    }
}
