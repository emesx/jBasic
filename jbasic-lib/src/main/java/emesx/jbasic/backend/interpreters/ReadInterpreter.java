package emesx.jbasic.backend.interpreters;

import emesx.jbasic.backend.Execution;
import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.intermediate.ir.statements.ReadStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadInterpreter implements Interpreter<ReadStatement> {
    private static final Logger LOG = LoggerFactory.getLogger(ReadInterpreter.class);

    @Override
    public void interpret(ReadStatement statement, Execution execution, SymbolTableStack symbols) {
        for (String variable : statement.getVariables()) {
            if (execution.getData().hasNext())
                symbols.set(variable, execution.getData().next());
            else {
                LOG.debug("No more DATA values found for {}", statement);
                execution.complete();
                return;
            }
        }
    }
}
