package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.Statement;

import java.util.List;


public class ForStatement extends Statement {
    private String variable;                 // added to scope if necessary
    private Expression initExpression;
    private Expression limitExpression;      // inclusive
    private Expression stepExpression;       // default 1, can be negative if end < init
    private List<Statement> statements;

    public ForStatement(String variable,
                        Expression initExpression,
                        Expression limitExpression,
                        Expression stepExpression,
                        List<Statement> statements) {

        this.variable = variable;
        this.initExpression = initExpression;
        this.limitExpression = limitExpression;
        this.stepExpression = stepExpression;
        this.statements = statements;
    }

    public String getVariable() {
        return variable;
    }

    public Expression getInitExpression() {
        return initExpression;
    }

    public Expression getLimitExpression() {
        return limitExpression;
    }

    public Expression getStepExpression() {
        return stepExpression;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
