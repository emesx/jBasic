package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.Statement;

public class LetStatement extends Statement {
    private String variable;
    private Expression expression;

    public LetStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public String getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }
}
