package emesx.jbasic.backend.interpreters;

import emesx.jbasic.backend.Execution;
import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.intermediate.ir.Statement;

@FunctionalInterface
public interface Interpreter<S extends Statement> {
    void interpret(S statement, Execution execution, SymbolTableStack symbols);
}
