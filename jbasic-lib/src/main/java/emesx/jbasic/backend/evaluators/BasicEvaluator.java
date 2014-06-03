package emesx.jbasic.backend.evaluators;

import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.expressions.*;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class BasicEvaluator implements Evaluator<Expression> {
    private final EvaluationManager manager;

    public BasicEvaluator() {
        manager = new EvaluationManager();

        manager.map(ConstantExpression.class,           (expr, symbols) -> expr.getValue())
                .map(VariableExpression.class,          (expr, symbols) -> symbols.get(expr.getVariableName()))
                .map(NegatingExpression.class,          (expr, symbols) -> -1 * (double) evaluate(expr.getExpression(), symbols))
                .map(AddExpression.class,               evalNumeric((a, b) -> a + b))
                .map(SubtractExpression.class,          evalNumeric((a, b) -> a - b))
                .map(MultiplyExpression.class,          evalNumeric((a, b) -> a * b))
                .map(DivideExpression.class,            evalNumeric((a, b) -> a / b))
                .map(ExponentiationExpression.class,    evalNumeric(Math::pow))
                .map(EqualityExpression.class,          evalObjects((a, b) -> a.equals(b)))
                .map(NotEqualityExpression.class,       evalObjects((a, b) -> !a.equals(b)))
                .map(GreaterThanExpression.class,       evalNumeric((a, b) -> a > b))
                .map(GreaterThanEqualExpression.class,  evalNumeric((a, b) -> a >= b))
                .map(LessThanExpression.class,          evalNumeric((a, b) -> a < b))
                .map(LessThanEqualExpression.class,     evalNumeric((a, b) -> a <= b))
                .map(FunctionExpression.class,          new FunctionEvaluator(this))
        ;
    }

    private <K extends BinaryExpression>
    Evaluator<K> evalNumeric(BiFunction<Double, Double, Object> op) {
        return (binExpr, symbols) -> op.apply(
                (double) evaluate(binExpr.getLhs(), symbols),
                (double) evaluate(binExpr.getRhs(), symbols)
        );
    }

    private <K extends BinaryExpression>
    Evaluator<K> evalObjects(BinaryOperator<Object> op) {
        return (binExpr, symbols) -> op.apply(
                evaluate(binExpr.getLhs(), symbols),
                evaluate(binExpr.getRhs(), symbols)
        );
    }


    @Override
    public Object evaluate(Expression expression, SymbolTableStack symbolTableStack) {
        return manager.evaluate(expression, symbolTableStack);
    }
}
