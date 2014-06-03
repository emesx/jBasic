package emesx.jbasic.intermediate.ir.expressions;

import emesx.jbasic.intermediate.ir.Expression;

import java.util.List;

public class FunctionExpression implements Expression {
    private final String functionName;
    private final List<Expression> arguments;   //actual, not formal

    public FunctionExpression(String functionName, List<Expression> argumentNames) {
        this.functionName = functionName;
        this.arguments = argumentNames;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Expression> getArguments() {
        return arguments;
    }
}
