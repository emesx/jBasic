package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Statement;

import java.util.List;

public class ReadStatement extends Statement {
    private List<String> variables;

    public ReadStatement(List<String> variables) {
        this.variables = variables;
    }

    public List<String> getVariables() {
        return variables;
    }
}
