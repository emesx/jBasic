package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Statement;

public class GotoStatement extends Statement {
    private int targetNumber;

    public GotoStatement(int targetNumber) {
        this.targetNumber = targetNumber;
    }

    public int getTargetNumber() {
        return targetNumber;
    }
}
