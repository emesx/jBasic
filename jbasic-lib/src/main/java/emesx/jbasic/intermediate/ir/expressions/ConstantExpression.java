package emesx.jbasic.intermediate.ir.expressions;

import emesx.jbasic.intermediate.ir.Expression;

public class ConstantExpression implements Expression {
    private final Object value;

    public ConstantExpression(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ConstantExpression{" + value + '}';
    }
}
