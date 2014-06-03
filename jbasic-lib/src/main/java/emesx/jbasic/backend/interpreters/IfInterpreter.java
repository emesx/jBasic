package emesx.jbasic.backend.interpreters;

import emesx.jbasic.backend.Execution;
import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.backend.evaluators.Evaluator;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.statements.IfStatement;

public class IfInterpreter implements Interpreter<IfStatement> {
    private Evaluator<Expression> evaluator;

    public IfInterpreter(Evaluator<Expression> evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public void interpret(IfStatement statement, Execution execution, SymbolTableStack symbols) {
        boolean condition = (boolean) evaluator.evaluate(statement.getCondition(), symbols);

        if (condition)
            execution.jump(statement.getTargetNumber());
    }
}
