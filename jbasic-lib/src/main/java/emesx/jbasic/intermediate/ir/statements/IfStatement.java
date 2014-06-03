package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.Statement;


public class IfStatement extends Statement {
    private Expression condition;
    private int targetNumber;

    public IfStatement(Expression condition, int targetNumber) {
        this.condition = condition;
        this.targetNumber = targetNumber;
    }

    public Expression getCondition() {
        return condition;
    }

    public int getTargetNumber() {
        return targetNumber;
    }
}
