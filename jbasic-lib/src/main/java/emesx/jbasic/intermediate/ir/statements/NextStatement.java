package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Statement;

public class NextStatement extends Statement {
    private String variable;

    public NextStatement(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }
}
