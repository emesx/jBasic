package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.*;
import emesx.jbasic.frontend.tokens.Number;
import emesx.jbasic.intermediate.ParserUtils;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.expressions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static emesx.jbasic.utils.Utils.fmt;


public class ExpressionParser {
    private static final Logger LOG = LoggerFactory.getLogger(ExpressionParser.class);

    private final Stack<Expression> expressions = new Stack<>();
    private final Stack<Operator> operators = new Stack<>();

    private Object lastConsumed;
    private int parenthesisCount;

    public Expression parse(TokenizingIterator iterator) {
        reset();

        while (isExpressionToken(iterator.peek())) {
            Token<?> token = iterator.next();

            if (isConstant(token)) {
                handleConstant(token);

            } else if (isIdentifier(token)) {
                handleIdentifier(iterator, (Identifier) token);

            } else if (isParenthesis(token)) {
                handleParenthesis(token);

            } else if (isOperator(token)) {
                handleOperator(iterator, (Operator) token);

            } else {
                throw new RuntimeException(fmt("Did not expect token %s in an expression", token));
            }
        }

        return handleRemainingOperators();
    }

    private void reset() {
        expressions.clear();
        operators.clear();
        lastConsumed = null;
        parenthesisCount = 0;
    }

    private boolean isExpressionToken(Token<?> token) {
        boolean result = ParserUtils.instanceOfAny(token, Identifier.class, Number.class, StringLiteral.class, Operator.class)
                && (token != Operator.SEPARATOR)
                && (token != Operator.STRING_DELIMITER); //TODO make this condition pretty

        return (parenthesisCount > 0) ? result : (result && (token != Operator.RIGHT_PARENTHESIS));
    }


    private boolean isConstant(Token<?> token) {
        return token instanceof Number || token instanceof StringLiteral;
    }

    private void handleConstant(Token<?> token) {
        Expression constant = new ConstantExpression(token.getValue());
        pushExpression(constant);
    }


    private boolean isIdentifier(Token<?> token) {
        return token instanceof Identifier;
    }

    private void handleIdentifier(TokenizingIterator iterator, Identifier identifier) {
        Expression varOrCall = parseVariableOrFunctionCall(iterator, identifier);
        pushExpression(varOrCall);
    }

    private Expression parseVariableOrFunctionCall(TokenizingIterator iterator, Identifier identifier) {
        // function
        if (iterator.peek() == Operator.LEFT_PARENTHESIS) {
            ParserUtils.consume(iterator, Operator.LEFT_PARENTHESIS);

            List<Expression> arguments = new ArrayList<>();

            while (iterator.peek() != Operator.RIGHT_PARENTHESIS) {
                Expression argument = new ExpressionParser().parse(iterator);
                arguments.add(argument);

                if (iterator.peek() == Operator.SEPARATOR)
                    ParserUtils.consume(iterator, Operator.SEPARATOR);   // , another expression
                else
                    break;                                               // no other expressions
            }

            ParserUtils.consume(iterator, Operator.RIGHT_PARENTHESIS);

            return new FunctionExpression(identifier.getValue(), arguments);
        }
        // variable
        else {
            return new VariableExpression(identifier.getValue());
        }
    }


    private boolean isOperator(Token<?> token) {
        return token instanceof Operator;
    }

    private void handleOperator(TokenizingIterator iterator, Operator operator) {
        // unary minus
        if (operator == Operator.SUBTRACTION &&
                (lastConsumed == null || ParserUtils.equalsAny(lastConsumed,
                        Operator.LEFT_PARENTHESIS,
                        Operator.EQUAL_TO, Operator.NOT_EQUAL_TO,
                        Operator.LESS_THAN, Operator.LESS_THAN_EQUAL,
                        Operator.GREATER_THAN, Operator.GREATER_THAN_EQUAL)

                )) {

            if (iterator.peek() instanceof Identifier) //TODO unary minus handling is terrible, `handleXXX()` should  return values, not mess with state
                handleIdentifier(iterator, (Identifier) iterator.next());
            else if (iterator.peek() instanceof Number)
                handleConstant(iterator.next());
            else if (iterator.peek() == Operator.LEFT_PARENTHESIS) {
                iterator.next();
                Expression e = new ExpressionParser().parse(iterator);
                pushExpression(e);
                ParserUtils.consume(iterator, Operator.RIGHT_PARENTHESIS);
            } else throw new RuntimeException(fmt("Unexpected token %s during unary minus parse", iterator.peek()));

            pushExpression(new NegatingExpression(expressions.pop()));
            return;
        }

        // normal binary operators
        if (operator != Operator.EXPONENTIATION) {
            int precedence = getOperatorPrecedence(operator);
            while (!operators.empty() && getOperatorPrecedence(operators.peek()) >= precedence) {
                pushNewExpression();
            }
        }
        // if it's exp then always pushExecution it straight away; others have to work it (above)
        pushOperator(operator);

    }


    private boolean isParenthesis(Token<?> token) {
        return token == Operator.LEFT_PARENTHESIS || token == Operator.RIGHT_PARENTHESIS;
    }

    private void handleParenthesis(Token<?> token) {
        if (token == Operator.LEFT_PARENTHESIS) {
            pushOperator((Operator) token);
            parenthesisCount += 1;

        } else if (token == Operator.RIGHT_PARENTHESIS && parenthesisCount > 0) { // if == 0 then we might be evaluating a function argument
            while (!operators.empty() && operators.peek() != Operator.LEFT_PARENTHESIS) {
                pushNewExpression();
            }

            operators.pop(); // pop (
            parenthesisCount -= 1;
        }
    }


    private void pushNewExpression() {
        Operator operator = operators.pop();
        Expression rhs = expressions.pop();
        Expression lhs = expressions.pop();

        // Mathematical
        if (operator == Operator.ADDITION)
            pushExpression(new AddExpression(lhs, rhs));

        else if (operator == Operator.SUBTRACTION)
            pushExpression(new SubtractExpression(lhs, rhs));

        else if (operator == Operator.MULTIPLICATION)
            pushExpression(new MultiplyExpression(lhs, rhs));

        else if (operator == Operator.DIVISION)
            pushExpression(new DivideExpression(lhs, rhs));

        else if (operator == Operator.EXPONENTIATION)
            pushExpression(new ExponentiationExpression(lhs, rhs));


            // Logical
        else if (operator == Operator.EQUAL_TO)
            pushExpression(new EqualityExpression(lhs, rhs));

        else if (operator == Operator.NOT_EQUAL_TO)
            pushExpression(new NotEqualityExpression(lhs, rhs));

        else if (operator == Operator.GREATER_THAN)
            pushExpression(new GreaterThanExpression(lhs, rhs));

        else if (operator == Operator.GREATER_THAN_EQUAL)
            pushExpression(new GreaterThanEqualExpression(lhs, rhs));

        else if (operator == Operator.LESS_THAN)
            pushExpression(new LessThanExpression(lhs, rhs));

        else if (operator == Operator.LESS_THAN_EQUAL)
            pushExpression(new LessThanEqualExpression(lhs, rhs));
    }

    private int getOperatorPrecedence(Operator operator) {
        if (operator == Operator.EXPONENTIATION)
            return 5;

        if (operator == Operator.MULTIPLICATION || operator == Operator.DIVISION)
            return 4;

        if (operator == Operator.ADDITION || operator == Operator.SUBTRACTION)
            return 3;

        if (operator == Operator.GREATER_THAN || operator == Operator.GREATER_THAN_EQUAL
                || operator == Operator.LESS_THAN || operator == Operator.LESS_THAN_EQUAL)
            return 2;

        if (operator == Operator.EQUAL_TO || operator == Operator.NOT_EQUAL_TO)
            return 1;

        return -1;
    }

    private void pushExpression(Expression expression) {
        lastConsumed = expression;
        expressions.push(expression);
    }

    private void pushOperator(Operator operator) {
        lastConsumed = operator;
        operators.push(operator);
    }

    private Expression handleRemainingOperators() {
        while (!operators.empty())
            pushNewExpression();

        return expressions.pop();
    }

}