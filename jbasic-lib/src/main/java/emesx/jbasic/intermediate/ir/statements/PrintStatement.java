package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.Statement;

import java.util.List;

public class PrintStatement extends Statement {
    private List<Expression> expressions;

    public PrintStatement(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
}
