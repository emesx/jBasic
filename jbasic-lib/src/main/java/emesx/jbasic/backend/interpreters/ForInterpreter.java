package emesx.jbasic.backend.interpreters;

import emesx.jbasic.backend.Execution;
import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.backend.evaluators.Evaluator;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.Statement;
import emesx.jbasic.intermediate.ir.statements.ForStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;


public class ForInterpreter implements Interpreter<ForStatement> {
    private static final Logger LOG = LoggerFactory.getLogger(ForInterpreter.class);
    private BasicInterpreter parent;
    private Evaluator<Expression> evaluator;

    public ForInterpreter(BasicInterpreter parent, Evaluator<Expression> evaluator) {
        this.parent = parent;
        this.evaluator = evaluator;
    }

    @Override
    public void interpret(ForStatement statement, Execution execution, SymbolTableStack symbols) {
        final String index = statement.getVariable();

        symbols.pushTable();
        if (!symbols.contains(index)) {
            double initial = (double) evaluator.evaluate(statement.getInitExpression(), symbols);
            symbols.set(index, initial);
        }

        final double limit = (double) evaluator.evaluate(statement.getLimitExpression(), symbols);
        final double step = (double) evaluator.evaluate(statement.getStepExpression(), symbols);
        LOG.debug("Evaluated limit {} with step {}", limit, step);

        List<Statement> loopStatements = statement.getStatements();
        int loopStart = loopStatements.get(0).getNumber();
        int loopEnd = loopStatements.get(loopStatements.size() - 1).getNext().getNumber();

        Supplier<Boolean> limitHolds = () -> step >= 0
                ? (double) symbols.get(index) <= limit
                : (double) symbols.get(index) >= limit;


        while (!execution.isComplete() && limitHolds.get()) {
            LOG.trace("Executing loop at {} with value {}", statement, symbols.get(index));
            execution.startContinuation(loopStart);

            parent.interpret(execution, symbols);
            symbols.set(index, (double) symbols.get(index) + step);

            execution.restoreContinuation();
        }

        if (!execution.isComplete()) {
            execution.jump(loopEnd);
            symbols.popTable();
        }
    }
}
