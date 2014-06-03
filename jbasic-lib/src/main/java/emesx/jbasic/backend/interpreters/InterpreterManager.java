package emesx.jbasic.backend.interpreters;

import emesx.jbasic.backend.Execution;
import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.intermediate.ir.Statement;

import java.util.HashMap;
import java.util.Map;

import static emesx.jbasic.utils.Utils.fmt;

public class InterpreterManager implements Interpreter<Statement> {
    private Map<Class<? extends Statement>, Interpreter<?>> interpreters;

    public InterpreterManager() {
        this.interpreters = new HashMap<>();
    }

    /**
     * Map an statement class to an interpreter instance
     */
    public <S extends Statement> InterpreterManager map(Class<S> clazz, Interpreter<S> interpreter) {
        interpreters.put(clazz, interpreter);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void interpret(Statement statement, Execution execution, SymbolTableStack symbols) {
        Interpreter evaluator = interpreters.get(statement.getClass());
        if (evaluator == null)
            throw new RuntimeException(fmt("Unexpected statement class: %s", statement.getClass()));

        evaluator.interpret(statement, execution, symbols);
    }
}
