package emesx.jbasic.intermediate.ir.expressions;

import emesx.jbasic.intermediate.ir.Expression;

public class VariableExpression implements Expression {
    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public String toString() {
        return "VariableExpression{" + variableName + '}';
    }
}
