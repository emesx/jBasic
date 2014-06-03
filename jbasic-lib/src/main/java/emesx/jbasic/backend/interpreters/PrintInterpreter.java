package emesx.jbasic.backend.interpreters;

import emesx.jbasic.backend.Execution;
import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.backend.evaluators.Evaluator;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.statements.PrintStatement;

public class PrintInterpreter implements Interpreter<PrintStatement> {
    private Evaluator<Expression> evaluator;

    public PrintInterpreter(Evaluator<Expression> evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public void interpret(PrintStatement statement, Execution execution, SymbolTableStack symbols) {
        for (Expression e : statement.getExpressions())
            System.out.print(evaluator.evaluate(e, symbols) + " ");

        System.out.println();
    }
}
