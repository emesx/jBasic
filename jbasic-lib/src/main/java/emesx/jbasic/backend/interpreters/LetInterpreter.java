package emesx.jbasic.backend.interpreters;

import emesx.jbasic.backend.Execution;
import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.backend.evaluators.Evaluator;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.statements.LetStatement;

public class LetInterpreter implements Interpreter<LetStatement> {
    private Evaluator<Expression> evaluator;

    public LetInterpreter(Evaluator<Expression> evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public void interpret(LetStatement statement, Execution execution, SymbolTableStack symbols) {
        Object value = evaluator.evaluate(statement.getExpression(), symbols);
        symbols.set(statement.getVariable(), value);
    }
}
