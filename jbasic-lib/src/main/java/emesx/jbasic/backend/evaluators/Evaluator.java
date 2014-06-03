package emesx.jbasic.backend.evaluators;

import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.intermediate.ir.Expression;

@FunctionalInterface
public interface Evaluator<E extends Expression> {
    Object evaluate(E expression, SymbolTableStack symbolTableStack);
}
