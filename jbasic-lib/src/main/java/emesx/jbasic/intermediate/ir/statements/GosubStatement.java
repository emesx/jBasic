package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Statement;

public class GosubStatement extends Statement {
    private int subLineNumber;

    public GosubStatement(int subLineNumber) {
        this.subLineNumber = subLineNumber;
    }

    public int getSubNumber() {
        return subLineNumber;
    }
}
