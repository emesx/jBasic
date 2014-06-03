package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.Statement;

import java.util.List;

public class DefStatement extends Statement {
    private String name;
    private List<String> parameters;
    private Expression expression;

    public DefStatement(String name, List<String> parameters, Expression expression) {
        this.name = name;
        this.parameters = parameters;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Expression getExpression() {
        return expression;
    }
}
