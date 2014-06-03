package emesx.jbasic.intermediate.ir.expressions;

import emesx.jbasic.intermediate.ir.Expression;

public class NotEqualityExpression extends BinaryExpression {
    public NotEqualityExpression(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }
}
