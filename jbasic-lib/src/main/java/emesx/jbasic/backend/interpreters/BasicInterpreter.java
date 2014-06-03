package emesx.jbasic.backend.interpreters;

import emesx.jbasic.backend.Execution;
import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.backend.evaluators.BasicEvaluator;
import emesx.jbasic.intermediate.ir.Statement;
import emesx.jbasic.intermediate.ir.statements.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicInterpreter {
    private static final Logger LOG = LoggerFactory.getLogger(BasicInterpreter.class);

    private final InterpreterManager manager = new InterpreterManager();

    public BasicInterpreter() {
        BasicEvaluator evaluator = new BasicEvaluator();

        manager.map(DataStatement.class,    (st, ex, sym) -> {})
                .map(DefStatement.class,    (st, ex, sym) -> {})
                .map(EndStatement.class,    (st, ex, sym) -> ex.complete())
                .map(ForStatement.class,    new ForInterpreter(this, evaluator))
                .map(GosubStatement.class,  (st, ex, sym) -> ex.startContinuation(st.getSubNumber()))
                .map(GotoStatement.class,   (st, ex, sym) -> ex.jump(st.getTargetNumber()))
                .map(IfStatement.class,     new IfInterpreter(evaluator))
                .map(LetStatement.class,    new LetInterpreter(evaluator))
                .map(NextStatement.class,   (st, ex, sym) -> ex.interrupt())
                .map(PrintStatement.class,  new PrintInterpreter(evaluator))
                .map(ReadStatement.class,   new ReadInterpreter())
                .map(RemStatement.class,    (st, ex, sym) -> {})
                .map(ReturnStatement.class, (st, ex, sym) -> ex.restoreContinuation())
                .map(StopStatement.class,   (st, ex, sym) -> ex.complete())
        ;
    }

    public void interpret(Execution execution, SymbolTableStack symbols) {
        while (!execution.isComplete() && !execution.isInterrupted()) {
            Statement statement = execution.proceed();
            LOG.trace("Interpreting statement {}", statement);
            manager.interpret(statement, execution, symbols);
        }
    }

}
