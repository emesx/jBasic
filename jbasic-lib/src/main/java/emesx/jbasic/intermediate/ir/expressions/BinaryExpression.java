package emesx.jbasic.intermediate.ir.expressions;

import emesx.jbasic.intermediate.ir.Expression;

public abstract class BinaryExpression implements Expression {
    private final Expression lhs, rhs;

    public BinaryExpression(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Expression getLhs() {
        return lhs;
    }

    public Expression getRhs() {
        return rhs;
    }
}
